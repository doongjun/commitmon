package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.app.data.UpdateUserDto
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun existsByName(name: String) = userRepository.existsByName(name)

    @Transactional(readOnly = true)
    fun get(
        id: Long,
        userFetchType: UserFetchType,
    ): GetUserDto =
        userRepository.findByIdOrNull(id)?.let { user -> GetUserDto.from(user, userFetchType) }
            ?: throw IllegalArgumentException("Failed to fetch user by id: $id")

    @Cacheable(value = ["userInfo"], key = "#name + '-' + #userFetchType.title")
    @Transactional(readOnly = true)
    fun getByName(
        name: String,
        userFetchType: UserFetchType,
    ): GetUserDto =
        userRepository.findByName(name)?.let { user -> GetUserDto.from(user, userFetchType) }
            ?: throw IllegalArgumentException("Failed to fetch user by name: $name")

    fun create(dto: CreateUserDto): Long {
        val user =
            userRepository.save(
                User(
                    name = dto.name,
                    totalCommitCount = dto.totalCommitCount,
                    followers = userRepository.findAllByNameIn(dto.followerNames),
                    following = userRepository.findAllByNameIn(dto.followingNames),
                ),
            )
        return user.id
    }

    fun update(
        id: Long,
        dto: UpdateUserDto,
    ) {
        val user =
            userRepository.findByIdOrNull(id)
                ?: throw IllegalArgumentException("Failed to fetch user by id: $id")

        user.update(
            totalCommitCount = dto.totalCommitCount,
            followers = userRepository.findAllByNameIn(dto.followerNames),
            following = userRepository.findAllByNameIn(dto.followingNames),
        )

        userRepository.save(user)
    }
}
