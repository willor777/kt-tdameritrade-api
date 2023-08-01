package com.willor777.td_api.data_objs.responses.optionchainresp


import com.google.gson.annotations.SerializedName

data class OptionContract(
    @SerializedName("ask")
    val ask: Double,
    @SerializedName("askSize")
    val askSize: Double,
    @SerializedName("bid")
    val bid: Double,
    @SerializedName("bidAskSize")
    val bidAskSize: String,
    @SerializedName("bidSize")
    val bidSize: Double,
    @SerializedName("closePrice")
    val closePrice: Double,
    @SerializedName("daysToExpiration")
    val daysToExpiration: Double,
    @SerializedName("deliverableNote")
    val deliverableNote: String,
    @SerializedName("delta")
    val delta: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("exchangeName")
    val exchangeName: String,
    @SerializedName("expirationDate")
    val expirationDate: Double,
    @SerializedName("expirationType")
    val expirationType: String,
    @SerializedName("gamma")
    val gamma: Double,
    @SerializedName("highPrice")
    val highPrice: Double,
    @SerializedName("inTheMoney")
    val inTheMoney: Boolean,
    @SerializedName("intrinsicValue")
    val intrinsicValue: Double,
    @SerializedName("last")
    val last: Double,
    @SerializedName("lastSize")
    val lastSize: Double,
    @SerializedName("lastTradingDay")
    val lastTradingDay: Double,
    @SerializedName("lowPrice")
    val lowPrice: Double,
    @SerializedName("mark")
    val mark: Double,
    @SerializedName("markChange")
    val markChange: Double,
    @SerializedName("markPercentChange")
    val markPercentChange: Double,
    @SerializedName("mini")
    val mini: Boolean,
    @SerializedName("multiplier")
    val multiplier: Double,
    @SerializedName("netChange")
    val netChange: Double,
    @SerializedName("nonStandard")
    val nonStandard: Boolean,
    @SerializedName("openInterest")
    val openInterest: Double,
    @SerializedName("openPrice")
    val openPrice: Double,
    @SerializedName("pennyPilot")
    val pennyPilot: Boolean,
    @SerializedName("percentChange")
    val percentChange: Double,
    @SerializedName("putCall")
    val putCall: String,
    @SerializedName("quoteTimeInLong")
    val quoteTimeInLong: Double,
    @SerializedName("rho")
    val rho: Double,
    @SerializedName("settlementType")
    val settlementType: String,
    @SerializedName("strikePrice")
    val strikePrice: Double,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("theoreticalOptionValue")
    val theoreticalOptionValue: Double,
    @SerializedName("theoreticalVolatility")
    val theoreticalVolatility: Double,
    @SerializedName("theta")
    val theta: Double,
    @SerializedName("timeValue")
    val timeValue: Double,
    @SerializedName("totalVolume")
    val totalVolume: Double,
    @SerializedName("tradeTimeInLong")
    val tradeTimeInLong: Double,
    @SerializedName("vega")
    val vega: Double,
    @SerializedName("volatility")
    val volatility: Double
)