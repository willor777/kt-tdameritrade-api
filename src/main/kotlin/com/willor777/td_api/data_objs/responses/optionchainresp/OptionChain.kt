package com.willor777.td_api.data_objs.responses.optionchainresp

data class OptionChain(
    val calls: List<OptionContract>,
    val puts: List<OptionContract>
)
