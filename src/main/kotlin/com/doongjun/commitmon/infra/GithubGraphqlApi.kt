package com.doongjun.commitmon.infra

import com.doongjun.commitmon.infra.data.GraphqlRequest
import com.doongjun.commitmon.infra.data.GraphqlResponse
import com.doongjun.commitmon.infra.data.UserFollowInfoResponse
import com.doongjun.commitmon.infra.data.UserFollowInfoVariables
import com.doongjun.commitmon.infra.data.UserFollowersResponse
import com.doongjun.commitmon.infra.data.UserFollowingResponse
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.nio.charset.Charset

@Component
class GithubGraphqlApi(
    private val githubGraphqlWebClient: WebClient,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        private val userFollowInfoQuery =
            ClassPathResource(
                "graphql/user-follow-info-query.graphql",
            ).getContentAsString(Charset.defaultCharset())

        private val userFollowersQuery =
            ClassPathResource(
                "graphql/user-followers-query.graphql",
            ).getContentAsString(Charset.defaultCharset())

        private val userFollowingQuery =
            ClassPathResource(
                "graphql/user-following-query.graphql",
            ).getContentAsString(Charset.defaultCharset())
    }

    fun fetchUserFollowInfo(
        username: String,
        size: Int,
    ): UserFollowInfoResponse.User {
        val variables =
            UserFollowInfoVariables(
                login = username,
                first = size,
            )
        val requestBody =
            GraphqlRequest(
                query = userFollowInfoQuery,
                variables = variables,
            )
        val response =
            githubGraphqlWebClient
                .post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<GraphqlResponse<UserFollowInfoResponse>>() {})
                .onErrorMap { error ->
                    log.error(error.message)
                    throw IllegalArgumentException("Failed to fetch user follow info: $error")
                }.block()!!

        if (isError(response)) {
            log.error("Failed to fetch user follow info: ${response.errors}")
            throw IllegalArgumentException("Failed to fetch user follow info: ${response.errors}")
        }

        return response.data.user!!
    }

    fun fetchUserFollowers(
        username: String,
        size: Int,
        after: String? = null,
    ): UserFollowersResponse.User {
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
        val response =
            githubGraphqlWebClient
                .post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<GraphqlResponse<UserFollowersResponse>>() {})
                .onErrorMap { error ->
                    log.error(error.message)
                    throw IllegalArgumentException("Failed to fetch user followers: $error")
                }.block()!!

        if (isError(response)) {
            log.error("Failed to fetch user followers: ${response.errors}")
            throw IllegalArgumentException("Failed to fetch user followers: ${response.errors}")
        }

        return response.data.user!!
    }

    fun fetchUserFollowing(
        username: String,
        size: Int,
        after: String? = null,
    ): UserFollowingResponse.User {
        val variables =
            UserFollowInfoVariables(
                login = username,
                first = size,
                after = after,
            )
        val requestBody =
            GraphqlRequest(
                query = userFollowingQuery,
                variables = variables,
            )
        val response =
            githubGraphqlWebClient
                .post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<GraphqlResponse<UserFollowingResponse>>() {})
                .onErrorMap { error ->
                    log.error(error.message)
                    throw IllegalArgumentException("Failed to fetch user following: $error")
                }.block()!!

        if (isError(response)) {
            log.error("Failed to fetch user following: ${response.errors}")
            throw IllegalArgumentException("Failed to fetch user following: ${response.errors}")
        }

        return response.data.user!!
    }

    private fun isError(response: GraphqlResponse<*>): Boolean = response.errors != null
}
