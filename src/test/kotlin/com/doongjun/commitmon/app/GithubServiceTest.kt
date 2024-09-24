package com.doongjun.commitmon.app

import com.doongjun.commitmon.infra.GithubGraphqlApi
import com.doongjun.commitmon.infra.GithubRestApi
import com.doongjun.commitmon.infra.data.FollowInfo
import com.doongjun.commitmon.infra.data.FollowNode
import com.doongjun.commitmon.infra.data.FollowPageInfo
import com.doongjun.commitmon.infra.data.UserCommitSearchResponse
import com.doongjun.commitmon.infra.data.UserFollowInfoResponse
import com.doongjun.commitmon.infra.data.UserFollowersResponse
import com.doongjun.commitmon.infra.data.UserFollowingResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GithubServiceTest {
    @InjectMocks
    private lateinit var githubService: GithubService

    @Mock
    private lateinit var githubRestApi: GithubRestApi

    @Mock
    private lateinit var githubGraphqlApi: GithubGraphqlApi

    @Test
    fun getUserCommitInfo_Test() {
        // given
        val username = "doongjun"
        val githubId = 1L
        val commitCount = 10L

        whenever(githubRestApi.fetchUserCommitSearchInfo(username)).then {
            return@then UserCommitSearchResponse(
                totalCount = commitCount,
                items =
                    listOf(
                        UserCommitSearchResponse.Item(
                            author =
                                UserCommitSearchResponse.Item.Author(
                                    id = githubId,
                                    login = username,
                                ),
                        ),
                    ),
            )
        }

        // when
        val result = githubService.getUserCommitInfo(username)

        // then
        assertThat(result.totalCommitCount).isEqualTo(commitCount)
        assertThat(result.githubId).isEqualTo(githubId)

        verify(githubRestApi, times(1)).fetchUserCommitSearchInfo(username)
    }

    @Test
    fun getUserFollowInfo_Test() {
        // given
        val username = "doongjun"
        val followerInfo =
            FollowInfo(
                totalCount = 2,
                pageInfo =
                    FollowPageInfo(
                        hasNextPage = false,
                        endCursor = "",
                    ),
                nodes =
                    listOf(
                        FollowNode(
                            login = "follower1",
                            databaseId = 1,
                        ),
                        FollowNode(
                            login = "follower2",
                            databaseId = 2,
                        ),
                    ),
            )
        val followingInfo =
            FollowInfo(
                totalCount = 1,
                pageInfo =
                    FollowPageInfo(
                        hasNextPage = false,
                        endCursor = "",
                    ),
                nodes =
                    listOf(
                        FollowNode(
                            login = "following1",
                            databaseId = 1,
                        ),
                    ),
            )

        whenever(githubGraphqlApi.fetchUserFollowInfo(username, 2)).then {
            return@then UserFollowInfoResponse.User(1L, followerInfo, followingInfo)
        }

        val result = githubService.getUserFollowInfo(username, 2)

        assertThat(result.userGithubId).isEqualTo(1)
        assertThat(result.followerGithubIds).containsExactly(1, 2)
        assertThat(result.followingGithubIds).containsExactly(1)

        verify(githubGraphqlApi, times(1)).fetchUserFollowInfo(username, 2)
    }

    @Test
    fun getUserFollowInfo_Paged_Test() {
        // given
        val username = "doongjun"

        whenever(githubGraphqlApi.fetchUserFollowInfo(username, 1)).then {
            return@then UserFollowInfoResponse.User(
                databaseId = 1,
                followers =
                    FollowInfo(
                        totalCount = 3,
                        pageInfo =
                            FollowPageInfo(
                                hasNextPage = true,
                                endCursor = "follower-cursor-1",
                            ),
                        nodes =
                            listOf(
                                FollowNode(
                                    login = "follower1",
                                    databaseId = 1,
                                ),
                            ),
                    ),
                following =
                    FollowInfo(
                        totalCount = 2,
                        pageInfo =
                            FollowPageInfo(
                                hasNextPage = true,
                                endCursor = "following-cursor-1",
                            ),
                        nodes =
                            listOf(
                                FollowNode(
                                    login = "following1",
                                    databaseId = 1,
                                ),
                            ),
                    ),
            )
        }

        whenever(githubGraphqlApi.fetchUserFollowers(username, 1, "follower-cursor-1")).then {
            return@then UserFollowersResponse.User(
                FollowInfo(
                    totalCount = 3,
                    pageInfo =
                        FollowPageInfo(
                            hasNextPage = true,
                            endCursor = "follower-cursor-2",
                        ),
                    nodes =
                        listOf(
                            FollowNode(
                                login = "follower2",
                                databaseId = 2,
                            ),
                        ),
                ),
            )
        }

        whenever(githubGraphqlApi.fetchUserFollowers(username, 1, "follower-cursor-2")).then {
            UserFollowersResponse.User(
                FollowInfo(
                    totalCount = 3,
                    pageInfo =
                        FollowPageInfo(
                            hasNextPage = false,
                            endCursor = "follower-cursor-3",
                        ),
                    nodes =
                        listOf(
                            FollowNode(
                                login = "follower3",
                                databaseId = 3,
                            ),
                        ),
                ),
            )
        }

        whenever(githubGraphqlApi.fetchUserFollowing(username, 1, "following-cursor-1")).then {
            return@then UserFollowingResponse.User(
                FollowInfo(
                    totalCount = 2,
                    pageInfo =
                        FollowPageInfo(
                            hasNextPage = false,
                            endCursor = "following-cursor-2",
                        ),
                    nodes =
                        listOf(
                            FollowNode(
                                login = "following2",
                                databaseId = 4,
                            ),
                        ),
                ),
            )
        }

        val result = githubService.getUserFollowInfo(username, 1)

        assertThat(result.userGithubId).isEqualTo(1)
        assertThat(result.followerGithubIds).containsExactly(1, 2, 3)
        assertThat(result.followingGithubIds).containsExactly(1, 4)

        verify(githubGraphqlApi, times(1)).fetchUserFollowInfo(username, 1)
        verify(githubGraphqlApi, times(1)).fetchUserFollowers(username, 1, "follower-cursor-1")
        verify(githubGraphqlApi, times(1)).fetchUserFollowers(username, 1, "follower-cursor-2")
        verify(githubGraphqlApi, times(1)).fetchUserFollowing(username, 1, "following-cursor-1")
    }
}
