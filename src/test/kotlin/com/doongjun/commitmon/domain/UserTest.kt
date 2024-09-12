package com.doongjun.commitmon.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserTest {
    private val githubId = 1L
    private val name = "doongjun"
    private val totalCommitCount = 10L
    private val followers =
        listOf(
            User(2L, "andrew"),
            User(3L, "david"),
        )
    private val following =
        listOf(
            User(3L, "david"),
            User(4L, "edward"),
        )

    @Test
    fun constructor_Default_Test() {
        // when
        val user = User(githubId, name)

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.githubId).isEqualTo(githubId)
        assertThat(user.name).isEqualTo(name)
        assertThat(user.createdDate).isNotNull()
        assertThat(user.lastModifiedDate).isNotNull()
    }

    @Test
    fun constructor_Test() {
        // when
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = totalCommitCount,
                followers = followers,
                following = following,
            )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.githubId).isEqualTo(githubId)
        assertThat(user.name).isEqualTo(name)
        assertThat(user.totalCommitCount).isEqualTo(totalCommitCount)
        assertThat(user.followers).containsExactlyElementsOf(followers)
        assertThat(user.following).containsExactlyElementsOf(following)
        assertThat(user.createdDate).isNotNull()
        assertThat(user.lastModifiedDate).isNotNull()
    }

    @Test
    fun update_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = totalCommitCount,
                followers = followers,
                following = following,
            )

        val updateName = "dongjunKim"
        val updateTotalCommitCount = 200L
        val updateFollowers =
            listOf(
                User(3L, "david"),
                User(5L, "frank"),
            )
        val updateFollowing =
            listOf(
                User(6L, "george"),
                User(7L, "harry"),
            )

        // when
        user.update(
            name = updateName,
            totalCommitCount = updateTotalCommitCount,
            followers = updateFollowers,
            following = updateFollowing,
        )

        // then

        assertThat(user.name).isEqualTo(updateName)
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.followers).containsExactlyElementsOf(updateFollowers)
        assertThat(user.following).containsExactlyElementsOf(updateFollowing)
    }
}
