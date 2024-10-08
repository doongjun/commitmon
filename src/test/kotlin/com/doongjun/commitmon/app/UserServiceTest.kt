package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.PatchUserDto
import com.doongjun.commitmon.app.data.UpdateUserDto
import com.doongjun.commitmon.domain.CommitmonLevel
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

class UserServiceTest : BaseAppTest() {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun existsByName_ThenIsTrue_Test() {
        // given
        val githubId = 1L
        val name = "doongjun"
        userRepository.save(User(githubId, name))
        clear()

        // when
        val exists = userService.existsByName(name)

        // then
        assertThat(exists).isTrue()
    }

    @Test
    fun existsByName_ThenIsFalse_Test() {
        // given
        val name = "doongjun"

        // when
        val exists = userService.existsByName(name)

        // then
        assertThat(exists).isFalse()
    }

    @Test
    fun get_Test() {
        // given
        val user = userRepository.save(User(1L, "doongjun"))
        clear()

        // when
        val dto = userService.get(user.id, UserFetchType.SOLO)

        // then
        assertThat(dto.name).isEqualTo(user.name)
        assertThat(dto.githubId).isEqualTo(user.githubId)
        assertThat(dto.totalCommitCount).isEqualTo(user.totalCommitCount)
    }

    @Test
    fun create_Test() {
        // given
        val dto =
            CreateUserDto(
                githubId = 1L,
                name = "doongjun",
                totalCommitCount = 10L,
            )

        // when
        val id = userService.create(dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(id)
        assertThat(findUser?.name).isEqualTo(dto.name)
        assertThat(findUser?.githubId).isEqualTo(dto.githubId)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(findUser?.followers).isEmpty()
        assertThat(findUser?.following).isEmpty()
    }

    @Test
    fun update_ThenIsNotJoinedUser_Test() {
        // given
        val user = userRepository.save(User(1L, "doongjun"))
        clear()

        val dto =
            UpdateUserDto(
                name = "doongjun",
                totalCommitCount = 10L,
                followerGithubIds = listOf(2L, 3L),
                followingGithubIds = listOf(3L, 4L),
            )

        // when
        userService.update(user.id, dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.name).isEqualTo(dto.name)
        assertThat(findUser?.githubId).isEqualTo(1L)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(findUser?.followers).isEmpty()
        assertThat(findUser?.following).isEmpty()
    }

    @Test
    fun update_ThenIsJoinedUser_Test() {
        // given
        val user = userRepository.save(User(1L, "doongjun"))
        val anotherUser1 = userRepository.save(User(2L, "andrew"))
        val anotherUser2 = userRepository.save(User(3L, "david"))
        val anotherUser3 = userRepository.save(User(4L, "edward"))
        clear()

        val dto =
            UpdateUserDto(
                name = "doongjunKim",
                totalCommitCount = 1600L,
                followerGithubIds = listOf(2L, 3L),
                followingGithubIds = listOf(3L, 4L),
            )

        // when
        userService.update(user.id, dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.name).isEqualTo(dto.name)
        assertThat(findUser?.githubId).isEqualTo(1L)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.PERFECT)
        assertThat(findUser?.followers).containsExactlyInAnyOrder(anotherUser1, anotherUser2)
        assertThat(findUser?.following).containsExactlyInAnyOrder(anotherUser2, anotherUser3)
    }

    @Test
    fun patch_Test() {
        // given
        val user = userRepository.save(User(1L, "doongjun"))
        val dto =
            PatchUserDto(
                totalCommitCount = 10L,
            )
        clear()

        // when
        userService.patch(user.id, dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.name).isEqualTo(user.name)
        assertThat(findUser?.githubId).isEqualTo(user.githubId)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
    }
}
