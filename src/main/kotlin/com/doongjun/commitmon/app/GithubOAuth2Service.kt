package com.doongjun.commitmon.app

import com.doongjun.commitmon.api.data.RedirectDestination
import com.doongjun.commitmon.infra.GithubOAuth2Api
import com.doongjun.commitmon.infra.GithubRestApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GithubOAuth2Service(
    private val githubOAuth2Api: GithubOAuth2Api,
    private val githubRestApi: GithubRestApi,
    @Value("\${app.github.oauth2.base-url}")
    private val githubOAuth2BaseUrl: String,
    @Value("\${app.github.oauth2.client-id}")
    private val githubClientId: String,
    @Value("\${app.github.oauth2.client-secret}")
    private val githubClientSecret: String,
) {
    fun getRedirectUrl(destination: RedirectDestination): String =
        "$githubOAuth2BaseUrl/authorize?client_id=$githubClientId&redirect_uri=${destination.callbackUrl}"

    fun getUserLogin(code: String): String {
        val userToken =
            githubOAuth2Api
                .fetchAccessToken(
                    code = code,
                    clientId = githubClientId,
                    clientSecret = githubClientSecret,
                ).accessToken

        return githubRestApi.fetchUserInfo(userToken)
    }
}
