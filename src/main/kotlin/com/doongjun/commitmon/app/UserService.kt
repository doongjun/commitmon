package com.doongjun.commitmon.app

import com.doongjun.commitmon.data.UserDto
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    fun create(
        githubId: Long,
        name: String,
    ) = UserDto.from(userRepository.save(User(githubId, name)))

    @Transactional(readOnly = true)
    fun existsByName(name: String) = userRepository.existsByName(name)
}
