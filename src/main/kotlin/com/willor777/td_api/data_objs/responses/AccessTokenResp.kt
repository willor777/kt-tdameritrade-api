package com.willor777.td_api.data_objs.responses


import com.google.gson.annotations.SerializedName

data class AccessTokenResp(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)