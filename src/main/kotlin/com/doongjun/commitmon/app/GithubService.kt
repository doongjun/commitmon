package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.GetFollowInfoDto
import com.doongjun.commitmon.infra.GithubGraphqlApi
import com.doongjun.commitmon.infra.GithubRestApi
import com.doongjun.commitmon.infra.data.FollowInfo
import org.springframework.stereotype.Service

@Service
class GithubService(
    private val githubRestApi: GithubRestApi,
    private val githubGraphqlApi: GithubGraphqlApi,
) {
    fun getCommitCount(username: String): Long = githubRestApi.fetchUserCommitTotalCount(username)

    fun getFollowInfo(
        username: String,
        pageSize: Int,
    ): GetFollowInfoDto {
        val (followers, following) =
            githubGraphqlApi
                .fetchUserFollowInfo(username, pageSize)

        return GetFollowInfoDto(
            followerGithubIds =
                fetchAllIds(followers) { cursor ->
                    githubGraphqlApi
                        .fetchUserFollowers(username, pageSize, cursor)
                        .followers
                },
            followingGithubIds =
                fetchAllIds(following) { cursor ->
                    githubGraphqlApi
                        .fetchUserFollowing(username, pageSize, cursor)
                        .following
                },
        )
    }

    private fun fetchAllIds(
        initialInfo: FollowInfo,
        fetchMore: (String?) -> FollowInfo,
    ): List<Long> {
        val ids = initialInfo.nodes.map { it.databaseId }.toMutableList()
        var hasNextPage = initialInfo.pageInfo.hasNextPage
        var cursor = initialInfo.pageInfo.endCursor

        while (hasNextPage) {
            val nextInfo = fetchMore(cursor)
            ids.addAll(nextInfo.nodes.map { it.databaseId })

            hasNextPage = nextInfo.pageInfo.hasNextPage
            cursor = nextInfo.pageInfo.endCursor
        }

        return ids
    }
}
