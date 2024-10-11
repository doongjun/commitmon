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
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GithubServiceTest : BaseAppTest() {
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
        val commitCount = 10L

        whenever(githubRestApi.fetchUserCommitSearchInfo(username)).then {
            return@then UserCommitSearchResponse(
                totalCount = commitCount,
            )
        }

        // when
        val result = githubService.getUserCommitInfo(username)

        // then
        assertThat(result.totalCommitCount).isEqualTo(commitCount)

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
                        ),
                        FollowNode(
                            login = "follower2",
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
                        ),
                    ),
            )

        whenever(githubGraphqlApi.fetchUserFollowInfo(username, 2)).then {
            return@then UserFollowInfoResponse.User(followerInfo, followingInfo)
        }

        val result = githubService.getUserFollowInfo(username, 2)

        assertThat(result.followerNames).containsExactly("follower1", "follower2")
        assertThat(result.followingNames).containsExactly("following1")
    }

    @Test
    fun getUserFollowInfo_ThenThrowException_Test() {
        // given
        val username = "doongjun"
        val size = 101

        // when
        val exception =
            assertThrows<IllegalArgumentException> {
                githubService.getUserFollowInfo(username, size)
            }

        // then
        assertThat(exception.message).isEqualTo("Size should be less than or equal to 100")
    }

    @Test
    fun getAllUserFollowInfo_Test() {
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
                        ),
                        FollowNode(
                            login = "follower2",
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
                        ),
                    ),
            )

        whenever(githubGraphqlApi.fetchUserFollowInfo(username, 2)).then {
            return@then UserFollowInfoResponse.User(followerInfo, followingInfo)
        }

        val result = githubService.getAllUserFollowInfo(username, 2)

        assertThat(result.followerNames).containsExactly("follower1", "follower2")
        assertThat(result.followingNames).containsExactly("following1")

        verify(githubGraphqlApi, times(1)).fetchUserFollowInfo(username, 2)
    }

    @Test
    fun getAllUserFollowInfo_Paged_Test() {
        // given
        val username = "doongjun"

        whenever(githubGraphqlApi.fetchUserFollowInfo(username, 1)).then {
            return@then UserFollowInfoResponse.User(
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
                            ),
                        ),
                ),
            )
        }

        val result = githubService.getAllUserFollowInfo(username, 1)

        assertThat(result.followerNames).containsExactly("follower1", "follower2", "follower3")
        assertThat(result.followingNames).containsExactly("following1", "following2")

        verify(githubGraphqlApi, times(1)).fetchUserFollowInfo(username, 1)
        verify(githubGraphqlApi, times(1)).fetchUserFollowers(username, 1, "follower-cursor-1")
        verify(githubGraphqlApi, times(1)).fetchUserFollowers(username, 1, "follower-cursor-2")
        verify(githubGraphqlApi, times(1)).fetchUserFollowing(username, 1, "following-cursor-1")
    }
}
