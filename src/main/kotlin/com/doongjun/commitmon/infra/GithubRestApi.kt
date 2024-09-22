package com.doongjun.commitmon.infra

import com.doongjun.commitmon.infra.data.UserCommitSearchResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GithubRestApi(
    private val githubRestWebClient: WebClient,
) {
    fun fetchUserCommitSearchInfo(username: String): UserCommitSearchResponse =
        githubRestWebClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/search/commits")
                    .queryParam("q", "author:$username")
                    .queryParam("per_page", 1)
                    .build()
            }.retrieve()
            .bodyToMono(UserCommitSearchResponse::class.java)
            .onErrorMap { error -> throw IllegalArgumentException("Failed to fetch user commit count: $error") }
            .block()!!
}
