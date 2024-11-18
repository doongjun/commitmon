package com.doongjun.commitmon.api.data

enum class RedirectDestination(
    val callbackUrl: String,
    private val clientUrl: String,
) {
    PRODUCTION(
        "https://commitmon.me/api/v1/account/oauth/github/callback/PRODUCTION",
        "https://commitmon-client.vercel.app/account",
    ),
    LOCAL(
        "https://commitmon.me/api/v1/account/oauth/github/callback/LOCAL",
        "http://localhost:3000/account",
    ),
    ;

    fun getClientUrl(
        accessToken: String,
        refreshToken: String,
    ): String = "$clientUrl?accessToken=$accessToken&refreshToken=$refreshToken"
}
