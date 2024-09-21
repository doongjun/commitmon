package com.doongjun.commitmon.app

import com.doongjun.commitmon.infra.GithubGraphqlApi
import com.doongjun.commitmon.infra.GithubRestApi
import com.doongjun.commitmon.infra.data.FollowInfo
import com.doongjun.commitmon.infra.data.FollowNode
import com.doongjun.commitmon.infra.data.FollowPageInfo
import com.doongjun.commitmon.infra.data.UserFollowInfoResponse
import com.doongjun.commitmon.infra.data.UserFollowersResponse
import com.doongjun.commitmon.infra.data.UserFollowingResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.BDDMockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class GithubServiceTest {
    @Autowired
    private lateinit var githubService: GithubService

    @MockBean
    private lateinit var githubRestApi: GithubRestApi

    @MockBean
    private lateinit var githubGraphqlApi: GithubGraphqlApi

    @Test
    fun getTotalCommitCount_Test() {
        // given
        val username = "doongjun"
        val commitCount = 10L

        given(githubRestApi.fetchUserCommitTotalCount(username))
            .willReturn(commitCount)

        // when
        val result = githubService.getTotalCommitCount(username)

        // then
        assertThat(result).isEqualTo(commitCount)
    }

    @Test
    fun getFollowInfo_Test() {
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

        given(githubGraphqlApi.fetchUserFollowInfo(username, 2))
            .willReturn(UserFollowInfoResponse.User(followerInfo, followingInfo))

        val result = githubService.getFollowInfo(username, 2)

        assertThat(result.followerGithubIds).containsExactly(1, 2)
        assertThat(result.followingGithubIds).containsExactly(1)

        verify(githubGraphqlApi, times(1)).fetchUserFollowInfo(username, 2)
    }

    @Test
    fun getFollowInfo_Paged_Test() {
        // given
        val username = "doongjun"

        given(githubGraphqlApi.fetchUserFollowInfo(username, 1))
            .willReturn(
                UserFollowInfoResponse.User(
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
                ),
            )
        given(githubGraphqlApi.fetchUserFollowers(username, 1, "follower-cursor-1"))
            .willReturn(
                UserFollowersResponse.User(
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
                ),
            )
        given(githubGraphqlApi.fetchUserFollowers(username, 1, "follower-cursor-2"))
            .willReturn(
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
                ),
            )
        given(githubGraphqlApi.fetchUserFollowing(username, 1, "following-cursor-1"))
            .willReturn(
                UserFollowingResponse.User(
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
                ),
            )

        val result = githubService.getFollowInfo(username, 1)

        assertThat(result.followerGithubIds).containsExactly(1, 2, 3)
        assertThat(result.followingGithubIds).containsExactly(1, 4)

        verify(githubGraphqlApi, times(1)).fetchUserFollowInfo(username, 1)
        verify(githubGraphqlApi, times(1)).fetchUserFollowers(username, 1, "follower-cursor-1")
        verify(githubGraphqlApi, times(1)).fetchUserFollowers(username, 1, "follower-cursor-2")
        verify(githubGraphqlApi, times(1)).fetchUserFollowing(username, 1, "following-cursor-1")
    }
}
