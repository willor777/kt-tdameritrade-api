package com.willor777.td_api.data_objs.responses.historicresp


import com.google.gson.annotations.SerializedName

data class Chart(
    @SerializedName("candles")
    val candles: List<Candle>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("symbol")
    val symbol: String
)