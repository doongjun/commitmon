package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.config.security.TokenProvider
import org.springframework.stereotype.Component

@Component
class AccountFacade(
    private val userService: UserService,
    private val githubService: GithubService,
    private val githubOAuth2Service: GithubOAuth2Service,
    private val tokenProvider: TokenProvider,
) {
    fun login(code: String): String {
        val userLogin = githubOAuth2Service.getUserLogin(code)
        val user = getOrCreateUser(userLogin)

        return tokenProvider.createAccessToken(user.id)
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
}
