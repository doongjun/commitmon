package com.doongjun.commitmon.event

import com.doongjun.commitmon.app.GithubService
import com.doongjun.commitmon.app.UserFetchType
import com.doongjun.commitmon.app.UserService
import com.doongjun.commitmon.app.data.UpdateUserDto
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ApplicationEventListener(
    private val userService: UserService,
    private val githubService: GithubService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Async
    @EventListener(UpdateUserInfo::class)
    fun handleUpdateUserInfo(event: UpdateUserInfo) {
        val user = userService.get(event.userId, UserFetchType.SOLO)

        log.info("Updating user info: ${user.name}")
        val (totalCommitCount) = githubService.getUserCommitInfo(user.name)
        val (followerNames, followingNames) = githubService.getUserFollowInfo(user.name, 100)

        UpdateUserDto(
            totalCommitCount = totalCommitCount,
            followerNames = followerNames,
            followingNames = followingNames,
        ).let { dto ->
            userService.update(user.id, dto)
        }
        log.info("User info updated: ${user.name}")
    }
}
