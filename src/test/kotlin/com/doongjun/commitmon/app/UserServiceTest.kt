package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.UpdateUserDto
import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.CommitmonLevel
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
        val name = "doongjun"
        userRepository.save(User(name))
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
        val name = "doongjun"
        val user = userRepository.save(User(name))
        clear()

        // when
        val dto = userService.get(user.id, UserFetchType.SOLO)

        // then
        assertThat(dto.name).isEqualTo(user.name)
    }

    @Test
    fun create_ThenIsNotJoinedUser_Test() {
        // given
        val name = "doongjun"
        val dto =
            CreateUserDto(
                name = name,
                totalCommitCount = 10L,
                followerNames = listOf("andrew", "david"),
                followingNames = listOf("david", "edward"),
            )

        // when
        val id = userService.create(dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(id)
        assertThat(findUser?.name).isEqualTo(dto.name)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(findUser?.followers).isEmpty()
        assertThat(findUser?.following).isEmpty()
    }

    @Test
    fun create_ThenIsJoinedUser_Test() {
        // given
        val name = "doongjun"
        val anotherUser1 = userRepository.save(User("andrew"))
        val anotherUser2 = userRepository.save(User("david"))
        val anotherUser3 = userRepository.save(User("edward"))
        val dto =
            CreateUserDto(
                name = name,
                totalCommitCount = 1600L,
                followerNames = listOf(anotherUser1.name, anotherUser2.name),
                followingNames = listOf(anotherUser2.name, anotherUser3.name),
            )
        clear()

        // when
        val id = userService.create(dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(id)
        assertThat(findUser?.name).isEqualTo(dto.name)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.PERFECT)
        assertThat(findUser?.followers).containsExactlyInAnyOrder(anotherUser1, anotherUser2)
        assertThat(findUser?.following).containsExactlyInAnyOrder(anotherUser2, anotherUser3)
    }

    @Test
    fun update_ThenIsNotJoinedUser_Test() {
        // given
        val name = "doongjun"
        val user = userRepository.save(User(name))
        val dto =
            UpdateUserDto(
                totalCommitCount = 10L,
                followerNames = listOf("andrew", "david"),
                followingNames = listOf("david", "edward"),
            )
        clear()

        // when
        userService.update(user.id, dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(findUser?.followers).isEmpty()
        assertThat(findUser?.following).isEmpty()
    }

    @Test
    fun update_ThenIsJoinedUser_Test() {
        // given
        val name = "doongjun"
        val user = userRepository.save(User(name))
        val anotherUser1 = userRepository.save(User("andrew"))
        val anotherUser2 = userRepository.save(User("david"))
        val anotherUser3 = userRepository.save(User("edward"))
        val dto =
            UpdateUserDto(
                totalCommitCount = 1600L,
                followerNames = listOf(anotherUser1.name, anotherUser2.name),
                followingNames = listOf(anotherUser2.name, anotherUser3.name),
            )
        clear()

        // when
        userService.update(user.id, dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.PERFECT)
        assertThat(findUser?.followers).containsExactlyInAnyOrder(anotherUser1, anotherUser2)
        assertThat(findUser?.following).containsExactlyInAnyOrder(anotherUser2, anotherUser3)
    }

    @Test
    fun update_ThenNotBeLeveledUp_Test() {
        // given
        val name = "doongjun"
        val user = userRepository.save(User(name = name, totalCommitCount = 200L))
        user.changeCommitmon(Commitmon.EGG)
        val dto =
            UpdateUserDto(
                totalCommitCount = 1600L,
                followerNames = emptyList(),
                followingNames = emptyList(),
            )
        clear()

        // when
        userService.update(user.id, dto)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.totalCommitCount).isEqualTo(dto.totalCommitCount)
        assertThat(findUser?.commitmon?.level).isEqualTo(CommitmonLevel.EGG)
        assertThat(findUser?.autoLevelUp).isFalse()
    }

    @Test
    fun changeCommitmon_ThenAutoLevelUpIsTrue_Test() {
        // given
        val name = "doongjun"
        val user = userRepository.save(User(name = name, totalCommitCount = 200L))
        val commitmon = Commitmon.PUKAMON
        clear()

        // when
        userService.changeCommitmon(user.id, commitmon)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.commitmon).isEqualTo(commitmon)
        assertThat(findUser?.autoLevelUp).isTrue()
    }

    @Test
    fun changeCommitmon_ThenAutoLevelUpIsFalse_Test() {
        // given
        val name = "doongjun"
        val user = userRepository.save(User(name = name, totalCommitCount = 200L))
        val commitmon = Commitmon.PICHIMON
        clear()

        // when
        userService.changeCommitmon(user.id, commitmon)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(user.id)
        assertThat(findUser?.commitmon).isEqualTo(commitmon)
        assertThat(findUser?.autoLevelUp).isFalse()
    }

    @Test
    fun changeCommitmon_ThenThrowException_Test() {
        // given
        val name = "doongjun"
        val user = userRepository.save(User(name = name, totalCommitCount = 200L))
        val commitmon = Commitmon.WARGREYMON
        clear()

        // when, then
        val exception =
            assertThrows<IllegalArgumentException> {
                userService.changeCommitmon(user.id, commitmon)
            }
        assertThat(exception.message).isEqualTo("Cannot change to higher level")
    }
}
