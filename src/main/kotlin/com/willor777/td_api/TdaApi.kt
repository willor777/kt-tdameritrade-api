package com.willor777.td_api

import com.willor777.td_api.common.Common
import com.willor777.td_api.common.NetworkClient
import com.willor777.td_api.data_objs.*
import com.willor777.td_api.data_objs.responses.AccessTokenResp
import com.willor777.td_api.data_objs.responses.OptionQuote
import com.willor777.td_api.data_objs.responses.RefreshTokenResp
import com.willor777.td_api.data_objs.responses.StockQuote
import com.willor777.td_api.data_objs.responses.historicresp.Chart
import com.willor777.td_api.data_objs.responses.optionchainresp.OptionChain
import com.willor777.td_api.data_objs.responses.optionchainresp.OptionContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.FormBody
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Request
import java.io.File
import java.net.URLDecoder


class TdaApi(
    val credsFilePath: String,
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    /* Notes
    * - To get access-token (token needed for real-time data) you first need a 'refresh-token'
    * - This must be done manually, you will click a link and go to TD website to log in
    * - After log-in, page will go to 'unavailable', copy the link and parse for 'code='
    * - Access Tokens (needed for real-time data) expire every 30m
    * - Refresh Tokens (needed to obtain Access Token) expire every 90days, and you will have to re login
    * */

    private lateinit var credentials: Credentials
    private val lock = Object()         // Lock for accessing credentials file

    init {
        initCredentialsFile()
    }

    private fun initCredentialsFile() {
        // Using lock just in case multiple instances of this class have been initialized
        synchronized(lock) {
            // load json creds file, update vals
            val creds = File(this.credsFilePath).readText()
            credentials = Common.gson.fromJson(creds, Credentials::class.java)
        }
    }

    private fun saveCredentials(newCreds: Credentials) {
        val credsJson = Common.gson.toJson(newCreds)

        // Locking might not be neccessary but doing it anyway
        synchronized(lock){
            File(credsFilePath).writeText(credsJson)
        }
    }

    private fun buildLoginUrl(): String {
        return "https://auth.tdameritrade.com/auth?response_type=code&" +
                "redirect_uri=${credentials.redirectUri}&client_id=${credentials.clientId}%40AMER.OAUTHAP"
    }

    /** Login to TD Ameritrade to receive refresh token. Token will allow you to obtain access token
     * with TdaApi.getAccessToken() which is needed for authenticated requests.
     * Refresh Tokens last for 90days */
    fun login() {
        // Login with url
        println("Please login with the following link. After webpage says unavailable, copy the link address to below")
        println()
        println(buildLoginUrl())
        println()
        val loginCodeResponse = readLine()!!.trim()

        // parse returned url for code
        val encodedString = loginCodeResponse?.substringAfterLast("?code=")!!
        val code = URLDecoder.decode(encodedString, "UTF-8")

        // use code make request to auth endpoint for access token
        val url = Endpoints.AUTH_REFRESH_TOKEN_ENDPOINT.url
            .toHttpUrl()
            .newBuilder()
            .build()

        val formBody = FormBody.Builder()
            .add("grant_type", "authorization_code")
            .add("access_type", "offline")
            .add("client_id", credentials.clientId)
            .add("redirect_uri", credentials.redirectUri)
            .add("code", code)

        val req = Request.Builder()
            .url(url)
            .post(formBody.build())
            .build()
        
        val resp = NetworkClient.getClient()
            .newCall(req)
            .execute()
            .body?.string()

        // Parse response
        val token = Common.gson.fromJson(resp, RefreshTokenResp::class.java)

        // Update credentials and save
        val newCreds = credentials.copy(
            refreshToken = token.refreshToken,
            refreshTokenExpiry = (token.refreshTokenExpiresIn * 1000) + System.currentTimeMillis(),
            accessToken = token.accessToken,
            accessTokenExpiry = (token.expiresIn.toLong() * 1000) + System.currentTimeMillis()
        )
        saveCredentials(newCreds)
    }

    // TODO Make Async
    private suspend fun getAccessToken(): String{
        // Check access token expiry, If still valid return
        if (this.credentials.accessTokenExpiry > System.currentTimeMillis()) {
            return this.credentials.accessToken
        }

        // Check refresh token expiry, If expired throw exception stopping program
        if (this.credentials.refreshTokenExpiry < System.currentTimeMillis()) {
            throw Exception("Refresh Token has expired. Please re-acquire with TdaApi().login()")
        }

        // Use refresh token to update access token
        val url = Endpoints.AUTH_REFRESH_TOKEN_ENDPOINT.url
            .toHttpUrl()
            .newBuilder()
            .build()

        val formBody = FormBody.Builder()
            .add("grant_type", "refresh_token")
            .add("client_id", credentials.clientId)
            .add("redirect_uri", credentials.redirectUri)
            .add("refresh_token", credentials.refreshToken)

        val req = Request.Builder()
            .url(url)
            .post(formBody.build())
            .build()

        val resp = NetworkClient.getClient()
            .newCall(req)
            .execute()

        if (!resp.isSuccessful){
            throw Exception("getAccessToken() NETWORK FAILURE! Failed to acquire Access Token!")
        }

        val body = resp.body?.string()
        // Parse response
        val token = Common.gson.fromJson(body, AccessTokenResp::class.java)

        // Update credentials and save
        val newCreds = credentials.copy(
            accessToken = token.accessToken,
            accessTokenExpiry = (token.expiresIn.toLong() * 1000) + System.currentTimeMillis()
        )
        saveCredentials(newCreds)
        return token.accessToken

    }

    /** Makes the request and returns the JSON String */
    private suspend fun getQuote(ticker: String): String? {
        // Simple fun to remove un-needed outer key which is the stock symbol
        val trimResponse = {r: String -> r.dropLast(1).removePrefix("{\"${ticker}\":") }

        val url = Endpoints.QUOTE_ENDPOINT.url.replace("{symbol}", ticker.uppercase())

        val accessToken = getAccessToken()

        val deferredResp = this.coroutineScope.async {
            NetworkClient.getClient().newCall(
                Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            ).execute()
        }

        val resp = deferredResp.await()

        if (!resp.isSuccessful) {
            return null
        }
        val body = resp.body?.string()
        return trimResponse(body!!)
    }

    suspend fun getStockQuote(ticker: String): StockQuote?{

        val jsonBody = getQuote(ticker) ?: return null

        return Common.gson.fromJson(jsonBody, StockQuote::class.java)
    }

    suspend fun getOptionChain(
        ticker: String,
        optionType: OptionType = OptionType.ALL,
        nStrikes: Int = 10,
        includeQuotes: Boolean = false,
        optionStrategy: OptionStrategy = OptionStrategy.SINGLE,
        spreadStrategyInterval: Int? = null,
        strikePrice: Int? = null,
        strikeRange: StrikeRange = StrikeRange.ALL,
        fromDate: String = "",
        toDate: String = "",
        ): OptionChain? {

        // Make async request and await
        val defResp = this.coroutineScope.async {
            // Build URL
            val url = Endpoints.OPTION_CHAIN_ENDPOINT.url.toHttpUrl().newBuilder()
                .addQueryParameter("symbol", ticker)
                .addQueryParameter("contractType", optionType.key)
                .addQueryParameter("strikeCount", nStrikes.toString())
                .addQueryParameter("includeQuotes", includeQuotes.toString())
                .addQueryParameter("strategy", optionStrategy.key)
                .addQueryParameter("interval", spreadStrategyInterval?.toString() ?: "")
                .addQueryParameter("strike", strikePrice?.toString() ?: "")
                .addQueryParameter("range", strikeRange.key)
                .addQueryParameter("fromDate", fromDate)
                .addQueryParameter("toDate", toDate)
                .build()

            NetworkClient.getClient().newCall(
                Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer ${getAccessToken()}")
                    .build()
            ).execute()
        }
        val resp = defResp.await()
        if (!resp.isSuccessful){
            return null
        }

        // Convert body to Map
        val body = resp.body?.string()
        val map = Common.gson.fromJson(body, Map::class.java)

        // Extract Expiry Dates
        val calls = map["callExpDateMap"]
        val puts = map["putExpDateMap"]

        val extractedCalls = mutableListOf<OptionContract>()
        val extractedPuts = mutableListOf<OptionContract>()

        // Loop through dates to obtain strikes for each date
        for (expiryDate in (calls as Map<*, *>).keys){
            val strikeMap = calls[expiryDate] as Map<*, *>
            val strikes = (calls[expiryDate] as Map<*, *>).keys

            // Loop through strikes, convert each to OptionContract object, add to list
            for (s in strikes){
                val c = (strikeMap[s] as List<*>)[0]

                // Lazy but saves ALOT of typing. Convert map to json, then json to OptionContract
                // TODO Fix this by creating a new OptionContract object, then fill in each value by map lookup
                val jsonC = Common.gson.toJson(c)
                val contractObj = Common.gson.fromJson(jsonC, OptionContract::class.java)
                extractedCalls.add(contractObj)
            }
        }
        // Loop through dates to obtain strikes for each date
        for (expiryDate in (puts as Map<*, *>).keys){
            val strikeMap = puts[expiryDate] as Map<*, *>
            val strikes = (puts[expiryDate] as Map<*, *>).keys

            // Loop through strikes, convert each to OptionContract object, add to list
            for (s in strikes){
                val c = (strikeMap[s] as List<*>)[0]

                // Lazy but saves ALOT of typing. Convert map to json, then json to OptionContract
                // TODO Fix this by creating a new OptionContract object, then fill in each value by map lookup
                val jsonC = Common.gson.toJson(c)
                val contractObj = Common.gson.fromJson(jsonC, OptionContract::class.java)
                extractedPuts.add(contractObj)
            }
        }

        return OptionChain(extractedCalls, extractedPuts)
    }

    suspend fun getOptionQuote(ticker: String): OptionQuote? {
        val jsonResp = getQuote(ticker) ?: return null

        return Common.gson.fromJson(jsonResp, OptionQuote::class.java)
    }

    /**Retreive Historic Chart Data for given ticker
     * periods: Number of periods to display of periodType, Valid Options as Follows, Defaults marked with *
     * day: 1, 2, 3, 4, 5, 10*
     * month: 1*, 2, 3, 6
     * year: 1*, 2, 3, 5, 10, 15, 20
     * ytd: 1
     *
     * freqType: M D W M, Defaults marked with *, Valid options follow
     * day: minute*
     * month: daily, weekly*
     * year: daily, weekly, monthly*
     * ytd: daily, weekly*
     *
     * freq: Size of candle, Valid values follow
     * minute: 1*, 5, 10, 15, 30
     * daily: 1*
     * weekly: 1*
     * monthly: 1*
     *
     *
     * **/
    suspend fun getHistoricData(
        ticker: String,
        periodType: PeriodType = PeriodType.DAY,
        periods: Int = 1,
        freqType: FrequencyType = FrequencyType.MINUTE,
        freq: Int = 5,
        endDate: Long? = null,
        startDate: Long? = null,
        prepost: Boolean = false
    ): Chart? {


        val defData = this.coroutineScope.async {
            val url = Endpoints.HISTORIC_DATA_ENDPOINT.url.replace("{symbol}", ticker)
                .toHttpUrl()
                .newBuilder()
                .addQueryParameter("periodType", periodType.key)
                .addQueryParameter("period", periods.toString())
                .addQueryParameter("frequencyType", freqType.key)
                .addQueryParameter("frequency", freq.toString())
                .addQueryParameter("endDate", endDate?.toString() ?: "")
                .addQueryParameter("startDate", startDate?.toString() ?: "")
                .addQueryParameter("prepost", prepost.toString())
                .build()

            NetworkClient.getClient().newCall(
                Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer ${getAccessToken()}")
                    .build()
            ).execute()
        }

        val resp = defData.await()
        if (!resp.isSuccessful) {
            return null
        }

        val jsonBody = resp.body?.string() ?: return null
        return Common.gson.fromJson(jsonBody, Chart::class.java)
    }

}

