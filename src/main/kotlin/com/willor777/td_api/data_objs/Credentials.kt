package com.willor777.td_api.data_objs


data class Credentials(
    val clientId: String,
    val redirectUri: String,
    val accountNumber: String,
    val refreshToken: String = "",
    val refreshTokenExpiry: Long = 0,
    val accessToken: String = "",
    val accessTokenExpiry: Long = 0
    )
