package com.willor777.td_api.data_objs.responses


import com.google.gson.annotations.SerializedName

data class StockQuote(
    @SerializedName("askId")
    val askId: String,
    @SerializedName("askPrice")
    val askPrice: Double,
    @SerializedName("askSize")
    val askSize: Int,
    @SerializedName("assetMainType")
    val assetMainType: String,
    @SerializedName("assetSubType")
    val assetSubType: String,
    @SerializedName("assetType")
    val assetType: String,
    @SerializedName("bidId")
    val bidId: String,
    @SerializedName("bidPrice")
    val bidPrice: Double,
    @SerializedName("bidSize")
    val bidSize: Int,
    @SerializedName("bidTick")
    val bidTick: String,
    @SerializedName("closePrice")
    val closePrice: Double,
    @SerializedName("cusip")
    val cusip: String,
    @SerializedName("delayed")
    val delayed: Boolean,
    @SerializedName("description")
    val description: String,
    @SerializedName("digits")
    val digits: Int,
    @SerializedName("divAmount")
    val divAmount: Double,
    @SerializedName("divDate")
    val divDate: String,
    @SerializedName("divYield")
    val divYield: Double,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("exchangeName")
    val exchangeName: String,
    @SerializedName("highPrice")
    val highPrice: Double,
    @SerializedName("lastId")
    val lastId: String,
    @SerializedName("lastPrice")
    val lastPrice: Double,
    @SerializedName("lastSize")
    val lastSize: Int,
    @SerializedName("lowPrice")
    val lowPrice: Double,
    @SerializedName("marginable")
    val marginable: Boolean,
    @SerializedName("mark")
    val mark: Double,
    @SerializedName("markChangeInDouble")
    val markChangeInDouble: Double,
    @SerializedName("markPercentChangeInDouble")
    val markPercentChangeInDouble: Double,
    @SerializedName("nAV")
    val nAV: Double,
    @SerializedName("netChange")
    val netChange: Double,
    @SerializedName("netPercentChangeInDouble")
    val netPercentChangeInDouble: Double,
    @SerializedName("openPrice")
    val openPrice: Double,
    @SerializedName("peRatio")
    val peRatio: Double,
    @SerializedName("quoteTimeInLong")
    val quoteTimeInLong: Long,
    @SerializedName("realtimeEntitled")
    val realtimeEntitled: Boolean,
    @SerializedName("regularMarketLastPrice")
    val regularMarketLastPrice: Double,
    @SerializedName("regularMarketLastSize")
    val regularMarketLastSize: Int,
    @SerializedName("regularMarketNetChange")
    val regularMarketNetChange: Double,
    @SerializedName("regularMarketPercentChangeInDouble")
    val regularMarketPercentChangeInDouble: Double,
    @SerializedName("regularMarketTradeTimeInLong")
    val regularMarketTradeTimeInLong: Long,
    @SerializedName("securityStatus")
    val securityStatus: String,
    @SerializedName("shortable")
    val shortable: Boolean,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("totalVolume")
    val totalVolume: Int,
    @SerializedName("tradeTimeInLong")
    val tradeTimeInLong: Long,
    @SerializedName("volatility")
    val volatility: Double,
    @SerializedName("52WkHigh")
    val wkHigh: Double,
    @SerializedName("52WkLow")
    val wkLow: Double
)