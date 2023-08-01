package com.willor777.td_api.data_objs.responses


import com.google.gson.annotations.SerializedName

data class OptionQuote(
    @SerializedName("askPrice")
    val askPrice: Double,
    @SerializedName("askSize")
    val askSize: Int,
    @SerializedName("assetMainType")
    val assetMainType: String,
    @SerializedName("assetType")
    val assetType: String,
    @SerializedName("bidPrice")
    val bidPrice: Double,
    @SerializedName("bidSize")
    val bidSize: Int,
    @SerializedName("closePrice")
    val closePrice: Double,
    @SerializedName("contractType")
    val contractType: String,
    @SerializedName("cusip")
    val cusip: String,
    @SerializedName("daysToExpiration")
    val daysToExpiration: Int,
    @SerializedName("delayed")
    val delayed: Boolean,
    @SerializedName("deliverables")
    val deliverables: String,
    @SerializedName("delta")
    val delta: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("digits")
    val digits: Int,
    @SerializedName("exchange")
    val exchange: String,
    @SerializedName("exchangeName")
    val exchangeName: String,
    @SerializedName("expirationDay")
    val expirationDay: Int,
    @SerializedName("expirationMonth")
    val expirationMonth: Int,
    @SerializedName("expirationYear")
    val expirationYear: Int,
    @SerializedName("gamma")
    val gamma: Double,
    @SerializedName("highPrice")
    val highPrice: Double,
    @SerializedName("impliedYield")
    val impliedYield: Double,
    @SerializedName("isPennyPilot")
    val isPennyPilot: Boolean,
    @SerializedName("lastPrice")
    val lastPrice: Double,
    @SerializedName("lastSize")
    val lastSize: Int,
    @SerializedName("lastTradingDay")
    val lastTradingDay: Long,
    @SerializedName("lowPrice")
    val lowPrice: Double,
    @SerializedName("mark")
    val mark: Double,
    @SerializedName("markChangeInDouble")
    val markChangeInDouble: Double,
    @SerializedName("markPercentChangeInDouble")
    val markPercentChangeInDouble: Double,
    @SerializedName("moneyIntrinsicValue")
    val moneyIntrinsicValue: Double,
    @SerializedName("multiplier")
    val multiplier: Double,
    @SerializedName("netChange")
    val netChange: Double,
    @SerializedName("netPercentChangeInDouble")
    val netPercentChangeInDouble: Double,
    @SerializedName("openInterest")
    val openInterest: Int,
    @SerializedName("openPrice")
    val openPrice: Double,
    @SerializedName("quoteTimeInLong")
    val quoteTimeInLong: Long,
    @SerializedName("realtimeEntitled")
    val realtimeEntitled: Boolean,
    @SerializedName("rho")
    val rho: Double,
    @SerializedName("securityStatus")
    val securityStatus: String,
    @SerializedName("settlementType")
    val settlementType: String,
    @SerializedName("strikePrice")
    val strikePrice: Double,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("theoreticalOptionValue")
    val theoreticalOptionValue: Double,
    @SerializedName("theta")
    val theta: Double,
    @SerializedName("timeValue")
    val timeValue: Double,
    @SerializedName("totalVolume")
    val totalVolume: Int,
    @SerializedName("tradeTimeInLong")
    val tradeTimeInLong: Long,
    @SerializedName("underlying")
    val underlying: String,
    @SerializedName("underlyingPrice")
    val underlyingPrice: Double,
    @SerializedName("uvExpirationType")
    val uvExpirationType: String,
    @SerializedName("vega")
    val vega: Double,
    @SerializedName("volatility")
    val volatility: Double
)