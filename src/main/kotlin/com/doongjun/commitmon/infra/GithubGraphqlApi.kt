package com.doongjun.commitmon.infra

import com.doongjun.commitmon.infra.data.GraphqlRequest
import com.doongjun.commitmon.infra.data.UserContributionsResponse
import com.doongjun.commitmon.infra.data.UserContributionsVariables
import com.doongjun.commitmon.infra.data.UserFollowInfoResponse
import com.doongjun.commitmon.infra.data.UserFollowInfoVariables
import com.doongjun.commitmon.infra.data.UserFollowersResponse
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.nio.charset.Charset
import java.time.Year

@Component
class GithubGraphqlApi(
    private val githubGraphqlWebClient: WebClient,
) {
    companion object {
        private val userContributionsQuery =
            ClassPathResource(
                "graphql/user-contributions-query.graphql",
            ).getContentAsString(Charset.defaultCharset())

        private val userFollowersQuery =
            ClassPathResource(
                "graphql/user-followers-query.graphql",
            ).getContentAsString(Charset.defaultCharset())
    }

    fun fetchUserContributionsByYear(
        username: String,
        year: Year,
    ): UserContributionsResponse? {
        val variables =
            UserContributionsVariables.of(
                login = username,
                year = year,
            )
        val requestBody =
            GraphqlRequest(
                query = userContributionsQuery,
                variables = variables,
            )

        return githubGraphqlWebClient
            .post()
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(UserContributionsResponse::class.java)
            .block()
    }

    fun fetchUserFollowInfo(
        username: String,
        size: Int,
    ): UserFollowInfoResponse? {
        val variables =
            UserFollowInfoVariables(
                login = username,
                first = size,
            )
        val requestBody =
            GraphqlRequest(
                query = userFollowersQuery,
                variables = variables,
            )

        return githubGraphqlWebClient
            .post()
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(UserFollowInfoResponse::class.java)
            .block()
    }

    fun fetchUserFollowers(
        username: String,
        size: Int,
        after: String? = null,
    ): UserFollowersResponse? {
        val variables =
            UserFollowInfoVariables(
                login = username,
                first = size,
                after = after,
            )
        val requestBody =
            GraphqlRequest(
                query = userFollowersQuery,
                variables = variables,
            )

        return githubGraphqlWebClient
            .post()
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(UserFollowersResponse::class.java)
            .block()
    }

    fun fetchUserFollowing(
        username: String,
        size: Int,
        after: String? = null,
    ): UserFollowersResponse? {
        val variables =
            UserFollowInfoVariables(
                login = username,
                first = size,
                after = after,
            )
        val requestBody =
            GraphqlRequest(
                query = userFollowersQuery,
                variables = variables,
            )

        return githubGraphqlWebClient
            .post()
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(UserFollowersResponse::class.java)
            .block()
    }
}
