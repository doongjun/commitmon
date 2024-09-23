package com.doongjun.commitmon.app

import org.springframework.stereotype.Component
import org.thymeleaf.spring6.SpringTemplateEngine

@Component
class AdventureFacade(
    private val userService: UserService,
    private val githubService: GithubService,
    private val svgTemplateEngine: SpringTemplateEngine,
) {
//    fun getCommitmonAdventure(username: String): String {
//        val (githubId, totalCommitCount) = githubService.getUserCommitInfo(username)
//        val (followerGithubIds, followingGithubIds) = githubService.getFollowInfo(username, 100)
//
//        val user =
//            userService.createOrUpdate(
//                CreateOrUpdateUserDto(
//                    githubId = githubId,
//                    name = username,
//                    totalCommitCount = totalCommitCount,
//                    followerGithubIds = followerGithubIds,
//                    followingGithubIds = followingGithubIds,
//                ),
//            )
//
//        val templates =
//            (user.mutualFollowers + user.toSimple()).joinToString { follower ->
//                svgTemplateEngine.process(
//                    "asset/${follower.commitmon.assetName}",
//                    Context().apply {
//                        setVariable("id", follower.id)
//                        setVariable("motion", AdventureGenerator.generateMotion())
//                        setVariable("y", AdventureGenerator.generateY(follower.commitmon.isFlying))
//                        setVariable("username", follower.name)
//                    },
//                )
//            }
//
//        val adventure =
//            svgTemplateEngine.process(
//                "adventure",
//                Context().apply {
//                    setVariable("commitmons", templates)
//                },
//            )
//        return adventure
//    }
//
//    fun getAdventure(username: String): String {
//        if (userService.existsByName(username)) {
//        }
//    }
}
