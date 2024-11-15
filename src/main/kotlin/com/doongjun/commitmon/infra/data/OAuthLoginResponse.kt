package com.doongjun.commitmon.infra.data

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthLoginResponse(
    @JsonProperty("access_token")
    val accessToken: String,
)
