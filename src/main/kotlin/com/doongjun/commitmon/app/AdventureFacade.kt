package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.app.data.PatchUserDto
import com.doongjun.commitmon.core.AdventureGenerator
import com.doongjun.commitmon.event.UpdateUserFollowInfo
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.nio.charset.Charset

@Component
class AdventureFacade(
    private val userService: UserService,
    private val githubService: GithubService,
    private val svgTemplateEngine: SpringTemplateEngine,
    private val publisher: ApplicationEventPublisher,
) {
    fun getAnimation(
        username: String,
        theme: Theme?,
    ): String {
        val (githubId, totalCommitCount) = githubService.getUserCommitInfo(username)

        handleUserData(username, githubId, totalCommitCount)

        return createAnimation(
            user = userService.getByGithubId(githubId),
            theme = theme ?: Theme.DESERT,
        )
    }

    private fun handleUserData(
        username: String,
        githubId: Long,
        totalCommitCount: Long,
    ) {
        runCatching {
            userService.getSimpleByGithubId(githubId)
        }.onSuccess { user ->
            PatchUserDto(
                name = username,
                totalCommitCount = totalCommitCount,
            ).let { dto ->
                userService.patch(user.id, dto)
            }
        }.onFailure {
            CreateUserDto(
                githubId = githubId,
                name = username,
                totalCommitCount = totalCommitCount,
            ).let { dto ->
                userService.create(dto)
            }
        }.also {
            publisher.publishEvent(UpdateUserFollowInfo(username))
        }
    }

    private fun createAnimation(
        user: GetUserDto,
        theme: Theme,
    ): String {
        val templates =
            (user.mutualFollowers + user.toSimple()).joinToString { u ->
                svgTemplateEngine.process(
                    "asset/${u.commitmon.assetName}",
                    Context().apply {
                        setVariable("id", u.id)
                        setVariable("motion", AdventureGenerator.generateMotion())
                        setVariable("y", AdventureGenerator.generateY(u.commitmon.isFlying))
                        setVariable("username", u.name)
                        setVariable("exp", u.exp)
                    },
                )
            }

        return svgTemplateEngine.process(
            "adventure",
            Context().apply {
                setVariable("templates", templates)
                setVariable("theme", ClassPathResource(
                    "static/theme/${theme.assetName}.svg",
                ).getContentAsString(Charset.defaultCharset()))
                setVariable("logo", ClassPathResource(
                    "static/logo.svg",
                ).getContentAsString(Charset.defaultCharset()))
            },
        )
    }
}
