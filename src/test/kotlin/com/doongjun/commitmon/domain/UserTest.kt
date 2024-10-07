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
        assertThat(user.totalCommitCount).isEqualTo(0)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.EGG)
    }

    @Test
    fun constructor_Test() {
        // when
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = totalCommitCount,
            )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.githubId).isEqualTo(githubId)
        assertThat(user.name).isEqualTo(name)
        assertThat(user.totalCommitCount).isEqualTo(totalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(user.followers).isEmpty()
        assertThat(user.following).isEmpty()
        assertThat(user.createdDate).isNotNull()
        assertThat(user.lastModifiedDate).isNotNull()
    }

    @Test
    fun patch_ThenEmptyToNotEmptyFollow_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = totalCommitCount,
            )

        val updateName = "dongjunKim"
        val updateTotalCommitCount = 200L

        // when
        user.patch(
            name = updateName,
            totalCommitCount = updateTotalCommitCount,
            followers = followers,
            following = following,
        )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.name).isEqualTo(updateName)
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.IN_TRAINING)
        assertThat(user.followers).containsExactlyElementsOf(followers)
        assertThat(user.following).containsExactlyElementsOf(following)
    }

    @Test
    fun patch_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = totalCommitCount,
            )
        user.patch(
            name = name,
            totalCommitCount = totalCommitCount,
            followers = followers,
            following = following,
        )

        val updateName = "dongjunKim"
        val updateTotalCommitCount = 200L
        val mutualFollower = User(4L, "frank")
        val updateFollowers =
            listOf(
                User(3L, "david"),
                mutualFollower,
            )
        val updateFollowing =
            listOf(
                mutualFollower,
                User(5L, "george"),
            )

        // when
        user.patch(
            name = updateName,
            totalCommitCount = updateTotalCommitCount,
            followers = updateFollowers,
            following = updateFollowing,
        )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.name).isEqualTo(updateName)
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.IN_TRAINING)
        assertThat(user.followers).containsExactlyElementsOf(updateFollowers)
        assertThat(user.following).containsExactlyElementsOf(updateFollowing)
        assertThat(user.mutualFollowers).containsExactlyElementsOf(listOf(mutualFollower))
    }

    @Test
    fun exp_Then0_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = 800,
            )

        // then
        assertThat(user.exp).isEqualTo(0)
    }

    @Test
    fun exp_Then10_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = 10,
            )

        // then
        assertThat(user.exp).isEqualTo(10)
    }

    @Test
    fun exp_Then25_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = 250,
            )

        // then
        assertThat(user.exp).isEqualTo(25)
    }

    @Test
    fun exp_Then50_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = 1200,
            )

        // then
        assertThat(user.exp).isEqualTo(50)
    }

    @Test
    fun exp_Then99_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = 3199,
            )

        // then
        assertThat(user.exp).isEqualTo(99)
    }

    @Test
    fun exp_Then100_Test() {
        // given
        val user =
            User(
                githubId = githubId,
                name = name,
                totalCommitCount = 3500,
            )

        // then
        assertThat(user.exp).isEqualTo(100)
    }
}
