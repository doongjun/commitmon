package com.doongjun.commitmon.app

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
    fun createTest() {
        // given
        val githubId = 1L
        val name = "doongjun"

        // when
        val userDto = userService.create(githubId, name)
        clear()

        // then
        val findUser = userRepository.findByIdOrNull(userDto.id)
        assertThat(findUser?.name).isEqualTo(name)
        assertThat(findUser?.githubId).isEqualTo(githubId)
    }

    @Test
    fun existsByName_thenIsTrue_Test() {
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
    fun existsByName_thenIsFalse_Test() {
        // given
        val name = "doongjun"

        // when
        val exists = userService.existsByName(name)

        // then
        assertThat(exists).isFalse()
    }
}
