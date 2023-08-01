package com.willor777.td_api.common

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit


internal object NetworkClient {

    private var client: OkHttpClient? = null


    /**
     * Returns the Singleton Instance of OkHttpclient
     */
    fun getClient(): OkHttpClient {
        if (client == null) {
            client = buildClient()
        }

        return client!!
    }


    /**
     * Used to initially build the OkHttpClient. Only called once during program life.
     */
    private fun buildClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10_000, TimeUnit.MILLISECONDS)
            .addInterceptor(createRetryInterceptor())
            .retryOnConnectionFailure(true)
            .followRedirects(true)      // VERY IMPORTANT FOR ANDROID
            .followSslRedirects(true)       // ALSO MAYBE
            .build()
    }
}

/** Creates the URL with query parameters added to the end of it (from Map<String,*>)
 *
 * Map of parameters is converted to a params string '?key=value&key=value'
 */
internal fun addParamsToUrl(urlString: String, params: Map<String, *>): String {

    var queryString = "?"

    for (k: String in params.keys) {
        queryString += "$k=${params[k].toString()}&"
    }

    return urlString + queryString.substring(0, queryString.length - 2)
}

fun createRetryInterceptor(): Interceptor {
    var retryCount = 0
    val maxRetries = 60

    return object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request: Request = chain.request()
            var response: Response? = null

            while (retryCount <= maxRetries) {
                try {
                    response = chain.proceed(request)

                    // Check if the response is successful
                    if (response.isSuccessful) {
                        return response
                    }
                    Thread.sleep(1000 * retryCount.toLong())
                } catch (e: Exception) {
                    // Retry the request
                    retryCount++
                    println("RETRY INTERCEPTOR TRIGGERED! Retry Count: $retryCount")
                }
            }

            // Return the last response, even if unsuccessful
            return response ?: throw IllegalStateException("No response received. Max Retries Reached!")
        }
    }
}