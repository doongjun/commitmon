package com.doongjun.commitmon.infra

import com.doongjun.commitmon.infra.data.OAuthLoginResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GithubOAuth2Api(
    private val githubOAuth2WebClient: WebClient,
) {
    fun fetchAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
    ): OAuthLoginResponse =
        githubOAuth2WebClient
            .post()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/access_token")
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("code", code)
                    .build()
            }.retrieve()
            .bodyToMono(OAuthLoginResponse::class.java)
            .onErrorMap { error -> throw IllegalArgumentException("Failed to fetch access token: $error") }
            .block()!!
}
