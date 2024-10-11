package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.GetUserCommitInfo
import com.doongjun.commitmon.app.data.GetUserFollowInfoDto
import com.doongjun.commitmon.infra.GithubGraphqlApi
import com.doongjun.commitmon.infra.GithubRestApi
import com.doongjun.commitmon.infra.data.FollowInfo
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class GithubService(
    private val githubRestApi: GithubRestApi,
    private val githubGraphqlApi: GithubGraphqlApi,
) {
    @Cacheable(value = ["userCommitInfo"], key = "#username")
    fun getUserCommitInfo(username: String): GetUserCommitInfo {
        val (totalCount) = githubRestApi.fetchUserCommitSearchInfo(username)

        return GetUserCommitInfo(
            totalCommitCount = totalCount,
        )
    }

    @Cacheable(value = ["userFollowInfo"], key = "#username")
    fun getUserFollowInfo(
        username: String,
        size: Int,
    ): GetUserFollowInfoDto {
        if (size > 100) {
            throw IllegalArgumentException("Size should be less than or equal to 100")
        }

        val (followers, following) =
            githubGraphqlApi
                .fetchUserFollowInfo(username, size)

        return GetUserFollowInfoDto(
            followerNames = followers.nodes.map { it.login },
            followingNames = following.nodes.map { it.login },
        )
    }

    fun getAllUserFollowInfo(
        username: String,
        pageSize: Int,
    ): GetUserFollowInfoDto {
        val (followers, following) =
            githubGraphqlApi
                .fetchUserFollowInfo(username, pageSize)

        return GetUserFollowInfoDto(
            followerNames =
                fetchAllNames(followers) { cursor ->
                    githubGraphqlApi
                        .fetchUserFollowers(username, pageSize, cursor)
                        .followers
                },
            followingNames =
                fetchAllNames(following) { cursor ->
                    githubGraphqlApi
                        .fetchUserFollowing(username, pageSize, cursor)
                        .following
                },
        )
    }

    private fun fetchAllNames(
        initialInfo: FollowInfo,
        fetchMore: (String?) -> FollowInfo,
    ): List<String> {
        val names = initialInfo.nodes.map { it.login }.toMutableList()
        var hasNextPage = initialInfo.pageInfo.hasNextPage
        var cursor = initialInfo.pageInfo.endCursor

        while (hasNextPage) {
            val nextInfo = fetchMore(cursor)
            names.addAll(nextInfo.nodes.map { it.login })

            hasNextPage = nextInfo.pageInfo.hasNextPage
            cursor = nextInfo.pageInfo.endCursor
        }

        return names
    }
}
