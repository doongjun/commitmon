package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateOrUpdateUserDto
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
    fun create_ThenIsNotJoinedUser_Test() {
        // given
        val request =
            CreateOrUpdateUserDto(
                githubId = 1L,
                name = "doongjun",
                totalCommitCount = 10L,
                followerGithubIds = listOf(2L, 3L),
                followingGithubIds = listOf(3L, 4L),
            )

        // when
        val userDto = userService.createOrUpdate(request)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(userDto.id)
        assertThat(findUser?.name).isEqualTo(request.name)
        assertThat(findUser?.githubId).isEqualTo(request.githubId)
        assertThat(findUser?.totalCommitCount).isEqualTo(request.totalCommitCount)
        assertThat(findUser?.followers).isEmpty()
        assertThat(findUser?.following).isEmpty()
    }

    @Test
    fun create_ThenIsJoinedUser_Test() {
        // given
        val anotherUser1 = userRepository.save(User(2L, "andrew"))
        val anotherUser2 = userRepository.save(User(3L, "david"))
        val anotherUser3 = userRepository.save(User(4L, "edward"))
        clear()

        val request =
            CreateOrUpdateUserDto(
                githubId = 1L,
                name = "doongjun",
                totalCommitCount = 10L,
                followerGithubIds = listOf(2L, 3L),
                followingGithubIds = listOf(3L, 4L),
            )

        // when
        val userDto = userService.createOrUpdate(request)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(userDto.id)
        assertThat(findUser?.name).isEqualTo(request.name)
        assertThat(findUser?.githubId).isEqualTo(request.githubId)
        assertThat(findUser?.totalCommitCount).isEqualTo(request.totalCommitCount)
        assertThat(findUser?.followers).containsExactlyInAnyOrder(anotherUser1, anotherUser2)
        assertThat(findUser?.following).containsExactlyInAnyOrder(anotherUser2, anotherUser3)
    }

    @Test
    fun update_ThenIsNotJoinedUser_Test() {
        // given
        userRepository.save(User(1L, "doongjun"))
        clear()

        val request =
            CreateOrUpdateUserDto(
                githubId = 1L,
                name = "doongjun",
                totalCommitCount = 10L,
                followerGithubIds = listOf(2L, 3L),
                followingGithubIds = listOf(3L, 4L),
            )

        // when
        val userDto = userService.createOrUpdate(request)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(userDto.id)
        assertThat(findUser?.name).isEqualTo(request.name)
        assertThat(findUser?.githubId).isEqualTo(request.githubId)
        assertThat(findUser?.totalCommitCount).isEqualTo(request.totalCommitCount)
        assertThat(findUser?.followers).isEmpty()
        assertThat(findUser?.following).isEmpty()
    }

    @Test
    fun update_ThenIsJoinedUser_Test() {
        // given
        userRepository.save(User(1L, "doongjun"))
        val anotherUser1 = userRepository.save(User(2L, "andrew"))
        val anotherUser2 = userRepository.save(User(3L, "david"))
        val anotherUser3 = userRepository.save(User(4L, "edward"))
        clear()

        val request =
            CreateOrUpdateUserDto(
                githubId = 1L,
                name = "doongjunKim",
                totalCommitCount = 10L,
                followerGithubIds = listOf(2L, 3L),
                followingGithubIds = listOf(3L, 4L),
            )

        // when
        val userDto = userService.createOrUpdate(request)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(userDto.id)
        assertThat(findUser?.name).isEqualTo(request.name)
        assertThat(findUser?.githubId).isEqualTo(request.githubId)
        assertThat(findUser?.totalCommitCount).isEqualTo(request.totalCommitCount)
        assertThat(findUser?.followers).containsExactlyInAnyOrder(anotherUser1, anotherUser2)
        assertThat(findUser?.following).containsExactlyInAnyOrder(anotherUser2, anotherUser3)
    }
}
