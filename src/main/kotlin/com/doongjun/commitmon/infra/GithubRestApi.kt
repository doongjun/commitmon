package com.doongjun.commitmon.infra

import com.doongjun.commitmon.infra.data.UserCommitTotalCountResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GithubRestApi(
    private val githubRestWebClient: WebClient,
) {
    fun fetchUserCommitTotalCount(username: String): Long =
        githubRestWebClient
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/search/commits")
                    .queryParam("q", "author:$username")
                    .queryParam("per_page", 1)
                    .build()
            }.retrieve()
            .bodyToMono(UserCommitTotalCountResponse::class.java)
            .onErrorMap { error -> throw IllegalArgumentException("Failed to fetch user commit count: $error") }
            .block()!!
            .totalCount
}
