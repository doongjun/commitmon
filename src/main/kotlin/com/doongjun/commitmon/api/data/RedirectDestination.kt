package com.doongjun.commitmon.api.data

enum class RedirectDestination(
    val callbackUrl: String,
    val clientUrl: String,
) {
    PRODUCTION(
        "https://commitmon.me/api/v1/account/oauth/github/callback/PRODUCTION",
        "https://commitmon-client.vercel.app",
    ),
    LOCAL(
        "http://localhost:8080/api/v1/account/oauth/github/callback/LOCAL",
        "http://localhost:3000",
    ),
}
