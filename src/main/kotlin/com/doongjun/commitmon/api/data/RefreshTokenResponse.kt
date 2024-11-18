package com.doongjun.commitmon.api.data

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    companion object {
        fun of(
            accessToken: String,
            refreshToken: String,
        ): RefreshTokenResponse = RefreshTokenResponse(accessToken, refreshToken)
    }
}
