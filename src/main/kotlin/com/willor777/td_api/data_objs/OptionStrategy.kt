package com.willor777.td_api.data_objs


//Passing a value returns a Strategy Chain. Possible values are SINGLE, ANALYTICAL
//(allows use of the volatility, underlyingPrice, interestRate, and daysToExpiration
//params to calculate theoretical values), COVERED, VERTICAL, CALENDAR, STRANGLE, STRADDLE,
//BUTTERFLY, CONDOR, DIAGONAL, COLLAR, or ROLL. Default is SINGLE.
enum class OptionStrategy(val key: String) {
    SINGLE("SINGLE"),
    ANALYTICAL("ANALYTICAL"),
    COVERED("COVERED"),
    VERTICAL("VERTICAL"),
    CALENDAR("CALENDAR"),
    STRANGLE("STRANGLE"),
    STRADDLE("STRADDLE"),
    BUTTERFLY("BUTTERFLY"),
    CONDOR("CONDOR"),
    DIAGONAL("DIAGONAL"),
    COLLAR("COLLAR"),
    ROLL("ROLL")
}