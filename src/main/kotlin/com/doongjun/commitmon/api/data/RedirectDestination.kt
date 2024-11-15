package com.doongjun.commitmon.api.data

enum class RedirectDestination(
    val callbackUrl: String,
) {
    PRODUCTION("https://commitmon.me/oauth/github/callback/PRODUCTION"),
    LOCAL("http://localhost:8080/oauth/github/callback/LOCAL"),
}
