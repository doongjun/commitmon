package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.core.AdventureGenerator
import com.doongjun.commitmon.event.UpdateUserInfo
import org.slf4j.LoggerFactory
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
    private val log = LoggerFactory.getLogger(javaClass)

    fun getAnimation(
        username: String,
        theme: Theme?,
        userFetchType: UserFetchType?,
    ): String =
        createAnimation(
            user =
                getOrCreateUser(
                    username = username,
                    userFetchType = userFetchType ?: UserFetchType.MUTUAL,
                ),
            theme = theme ?: Theme.GRASSLAND,
        )

    private fun getOrCreateUser(
        username: String,
        userFetchType: UserFetchType,
    ): GetUserDto =
        runCatching {
            userService.getByName(
                name = username,
                userFetchType = userFetchType,
            )
        }.onSuccess { existsUser ->
            publisher.publishEvent(UpdateUserInfo(existsUser.id))
        }.getOrElse {
            log.info("Creating user: $username")
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

            log.info("User created: $username")
            userService.get(userId, userFetchType)
        }

    private fun createAnimation(
        user: GetUserDto,
        theme: Theme,
    ): String {
        val templates =
            (user.fetchedUsers + user.toSimple()).joinToString { u ->
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
                setVariable("username", user.name)
                setVariable("totalCommitCount", user.totalCommitCount)
                setVariable(
                    "theme",
                    ClassPathResource(
                        "static/theme/${theme.assetName}.svg",
                    ).getContentAsString(Charset.defaultCharset()),
                )
                setVariable("templates", templates)
            },
        )
    }
}
