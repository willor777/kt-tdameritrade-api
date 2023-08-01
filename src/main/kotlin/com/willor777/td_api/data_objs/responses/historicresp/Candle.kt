package com.willor777.td_api.data_objs.responses.historicresp


import com.google.gson.annotations.SerializedName

data class Candle(
    @SerializedName("close")
    val close: Double,
    @SerializedName("datetime")
    val datetime: Long,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("open")
    val `open`: Double,
    @SerializedName("volume")
    val volume: Int
)