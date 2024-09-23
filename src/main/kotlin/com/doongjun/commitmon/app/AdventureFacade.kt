package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.app.data.PatchUserDto
import com.doongjun.commitmon.core.AdventureGenerator
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Component
class AdventureFacade(
    private val userService: UserService,
    private val githubService: GithubService,
    private val svgTemplateEngine: SpringTemplateEngine,
) {
    fun getAnimation(username: String): String {
        val (githubId, totalCommitCount) = githubService.getUserCommitInfo(username)

        return runCatching {
            val user = userService.getByGithubId(githubId)
            PatchUserDto(
                name = username,
                totalCommitCount = totalCommitCount,
            ).let { dto ->
                userService.patch(user.id, dto)
            }
            createAnimation(userService.get(user.id))
        }.getOrElse { e ->
            if (e is IllegalArgumentException) {
                CreateUserDto(
                    githubId = githubId,
                    name = username,
                    totalCommitCount = totalCommitCount,
                ).let { dto ->
                    createAnimation(userService.get(userService.create(dto)))
                }
            } else {
                throw e
            }
        }
    }

    private fun createAnimation(user: GetUserDto): String {
        val templates =
            (user.mutualFollowers + user.toSimple()).joinToString { follower ->
                svgTemplateEngine.process(
                    "asset/${follower.commitmon.assetName}",
                    Context().apply {
                        setVariable("id", follower.id)
                        setVariable("motion", AdventureGenerator.generateMotion())
                        setVariable("y", AdventureGenerator.generateY(follower.commitmon.isFlying))
                        setVariable("username", follower.name)
                    },
                )
            }

        return svgTemplateEngine.process(
            "adventure",
            Context().apply {
                setVariable("templates", templates)
            },
        )
    }
}
