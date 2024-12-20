package com.doongjun.commitmon.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserTest {
    private val name = "doongjun"
    private val totalCommitCount = 10L
    private val followers =
        listOf(
            User("andrew"),
            User("david"),
        )
    private val following =
        listOf(
            User("david"),
            User("edward"),
        )

    @Test
    fun constructor_Default_Test() {
        // when
        val user = User(name)

        // then
        assertThat(user.id).isNotNull()
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
                name = name,
                totalCommitCount = totalCommitCount,
            )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.name).isEqualTo(name)
        assertThat(user.totalCommitCount).isEqualTo(totalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(user.followers).isEmpty()
        assertThat(user.following).isEmpty()
        assertThat(user.createdDate).isNotNull()
        assertThat(user.lastModifiedDate).isNotNull()
    }

    @Test
    fun update_ThenEmptyToNotEmptyFollow_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = totalCommitCount,
            )

        val updateTotalCommitCount = 200L

        // when
        user.update(
            totalCommitCount = updateTotalCommitCount,
            followers = followers,
            following = following,
        )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.IN_TRAINING)
        assertThat(user.followers).containsExactlyElementsOf(followers)
        assertThat(user.following).containsExactlyElementsOf(following)
    }

    @Test
    fun update_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = totalCommitCount,
                followers = followers,
                following = following,
            )

        val updateTotalCommitCount = 200L
        val mutualFollower = User("frank")
        val updateFollowers =
            listOf(
                User("david"),
                mutualFollower,
            )
        val updateFollowing =
            listOf(
                mutualFollower,
                User("george"),
            )

        // when
        user.update(
            totalCommitCount = updateTotalCommitCount,
            followers = updateFollowers,
            following = updateFollowing,
        )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.IN_TRAINING)
        assertThat(user.followers).containsExactlyElementsOf(updateFollowers)
        assertThat(user.following).containsExactlyElementsOf(updateFollowing)
        assertThat(user.mutualFollowers).containsExactlyElementsOf(listOf(mutualFollower))
    }

    @Test
    fun update_ThenBeLeveledUp_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = 200L,
            )
        user.changeCommitmon(Commitmon.KOROMON)

        val updateTotalCommitCount = 800L
        // when
        user.update(
            totalCommitCount = updateTotalCommitCount,
            followers = emptyList(),
            following = emptyList(),
        )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.commitmon).isEqualTo(Commitmon.GREYMON)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.CHAMPION)
        assertThat(user.autoLevelUp).isTrue()
    }

    @Test
    fun update_ThenNotBeLeveledUp_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = 200L,
            )
        user.changeCommitmon(Commitmon.BOTAMON)

        val updateTotalCommitCount = 400L
        // when
        user.update(
            totalCommitCount = updateTotalCommitCount,
            followers = emptyList(),
            following = emptyList(),
        )

        // then
        assertThat(user.id).isNotNull()
        assertThat(user.totalCommitCount).isEqualTo(updateTotalCommitCount)
        assertThat(user.commitmon.level).isEqualTo(CommitmonLevel.BABY)
        assertThat(user.autoLevelUp).isFalse()
    }

    @Test
    fun changeCommitmon_ThenSameLevel_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = 200L,
            )

        // when
        user.changeCommitmon(Commitmon.KOROMON)

        // then
        assertThat(user.commitmon).isEqualTo(Commitmon.KOROMON)
        assertThat(user.autoLevelUp).isTrue()
    }

    @Test
    fun changeCommitmon_ThenLowerLevel_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = 200L,
            )

        // when
        user.changeCommitmon(Commitmon.EGG)

        // then
        assertThat(user.commitmon).isEqualTo(Commitmon.EGG)
        assertThat(user.autoLevelUp).isFalse()
    }

    @Test
    fun changeCommitmon_ThenHigherLevel_Test() {
        // given
        val user =
            User(
                name = name,
                totalCommitCount = 200L,
            )

        // when, then
        val exception =
            assertThrows<IllegalArgumentException> {
                user.changeCommitmon(Commitmon.ATLURKABUTERIMON)
            }
        assertThat(exception.message).isEqualTo("Cannot change to higher level")
    }

    @Test
    fun exp_Then0_Test() {
        // given
        val user =
            User(
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
                name = name,
                totalCommitCount = 3500,
            )

        // then
        assertThat(user.exp).isEqualTo(100)
    }
}
