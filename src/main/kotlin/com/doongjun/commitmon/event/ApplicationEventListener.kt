package com.doongjun.commitmon.event

import com.doongjun.commitmon.app.GithubService
import com.doongjun.commitmon.app.UserService
import com.doongjun.commitmon.app.data.PatchUserDto
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ApplicationEventListener(
    private val userService: UserService,
    private val githubService: GithubService,
) {
    @Async
    @EventListener(UpdateUserFollowInfo::class)
    fun handleUpdateUserFollowInfo(event: UpdateUserFollowInfo) {
        val (githubId, followerGithubIds, followingGithubIds) = githubService.getUserFollowInfo(event.username, 100)

        val user = userService.getSimpleByGithubId(githubId)
        PatchUserDto(
            followerGithubIds = followerGithubIds,
            followingGithubIds = followingGithubIds,
        ).let { dto ->
            userService.patch(user.id, dto)
        }
    }
}
