package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.AuthDto
import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.config.security.TokenProvider
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.stereotype.Component

@Component
class AccountFacade(
    private val userService: UserService,
    private val githubService: GithubService,
    private val githubOAuth2Service: GithubOAuth2Service,
    private val tokenProvider: TokenProvider,
) {
    fun login(code: String): AuthDto {
        val userLogin = githubOAuth2Service.getUserLogin(code)
        val user = getOrCreateUser(userLogin)

        return AuthDto(
            accessToken = tokenProvider.createAccessToken(user.id),
            refreshToken = tokenProvider.createRefreshToken(user.id),
        )
    }

    private fun getOrCreateUser(username: String): GetUserDto =
        runCatching {
            userService.getByName(
                name = username,
                userFetchType = UserFetchType.SOLO,
            )
        }.getOrElse {
            val (totalCommitCount) = githubService.getUserCommitInfo(username)
            val (followerNames, followingNames) = githubService.getUserFollowInfo(username, 100)
            val userId =
                CreateUserDto(
                    name = username,
                    totalCommitCount = totalCommitCount,
                    followerNames = followerNames,
                    followingNames = followingNames,
                ).let { dto ->
                    userService.create(dto)
                }
            userService.get(userId, UserFetchType.SOLO)
        }

    fun refresh(token: String): AuthDto {
        val refreshToken =
            tokenProvider.getRefreshToken(token)
                ?: throw AccountExpiredException("Refresh token is expired")

        return AuthDto(
            accessToken = tokenProvider.createAccessToken(refreshToken.userId!!),
            refreshToken =
                when (refreshToken.isLessThanWeek()) {
                    true -> {
                        val user = userService.get(refreshToken.userId, UserFetchType.SOLO)
                        tokenProvider.expireRefreshToken(token)
                        tokenProvider.createRefreshToken(user.id)
                    }
                    false -> refreshToken.token!!
                },
        )
    }
}
