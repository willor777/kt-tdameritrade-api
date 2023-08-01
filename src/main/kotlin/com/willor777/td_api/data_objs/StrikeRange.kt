package com.willor777.td_api.data_objs

enum class StrikeRange(val key: String) {

    IN_THE_MONEY("ITM"),
    NEAR_THE_MONEY("NTM"),
    OUT_THE_MONEY("OTM"),
    STRIKES_ABOVE_MARKET("SAK"),
    STRIKES_BELOW_MARKET("SBK"),
    STRIKES_NEAR_MARKET("SNK"),
    ALL("ALL")

//    ITM: In-the-money
//    NTM: Near-the-money
//    OTM: Out-of-the-money
//    SAK: Strikes Above Market
//    SBK: Strikes Below Market
//    SNK: Strikes Near Market
//    ALL: All Strikes
}