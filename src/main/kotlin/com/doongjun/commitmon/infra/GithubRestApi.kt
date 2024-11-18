package com.doongjun.commitmon.infra

import com.doongjun.commitmon.infra.data.UserCommitSearchResponse
import com.doongjun.commitmon.infra.data.UserInfoResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GithubRestApi(
    private val githubRestWebClient: WebClient,
    @Value("\${app.github.token}")
    private val githubToken: String,
) {
    fun fetchUserInfo(userToken: String): String =
        githubRestWebClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/user")
                    .build()
            }.headers { headers ->
                headers.add("Authorization", "Bearer $userToken")
            }.retrieve()
            .bodyToMono(UserInfoResponse::class.java)
            .onErrorMap { error -> throw IllegalArgumentException("Failed to fetch user: $error") }
            .block()!!
            .login

    fun fetchUserCommitSearchInfo(username: String): UserCommitSearchResponse =
        githubRestWebClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/search/commits")
                    .queryParam("q", "author:$username")
                    .queryParam("per_page", 1)
                    .build()
            }.headers { headers ->
                headers.add("Authorization", "Bearer $githubToken")
            }.retrieve()
            .bodyToMono(UserCommitSearchResponse::class.java)
            .onErrorMap { error -> throw IllegalArgumentException("Failed to fetch user commit count: $error") }
            .block()!!
}
