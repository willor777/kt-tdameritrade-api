package com.willor777.td_api.data_objs

enum class Endpoints(val url: String) {
    AUTH_REFRESH_TOKEN_ENDPOINT("https://api.tdameritrade.com/v1/oauth2/token"),
    QUOTE_ENDPOINT("https://api.tdameritrade.com/v1/marketdata/{symbol}/quotes"),
    OPTION_CHAIN_ENDPOINT("https://api.tdameritrade.com/v1/marketdata/chains"),
    HISTORIC_DATA_ENDPOINT("https://api.tdameritrade.com/v1/marketdata/{symbol}/pricehistory")
}