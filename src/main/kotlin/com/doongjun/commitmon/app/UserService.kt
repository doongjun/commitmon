package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetSimpleUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.app.data.UpdateUserDto
import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val cacheManager: CacheManager,
) {
    @Transactional(readOnly = true)
    fun existsByName(name: String) = userRepository.existsByName(name)

    @Transactional(readOnly = true)
    fun getSimple(id: Long): GetSimpleUserDto =
        userRepository.findByIdOrNull(id)?.let { user -> GetSimpleUserDto.from(user) }
            ?: throw NoSuchElementException("Failed to fetch user by id: $id")

    @Transactional(readOnly = true)
    fun get(
        id: Long,
        userFetchType: UserFetchType,
    ): GetUserDto =
        userRepository.findByIdOrNull(id)?.let { user -> GetUserDto.from(user, userFetchType) }
            ?: throw NoSuchElementException("Failed to fetch user by id: $id")

    @Cacheable(value = ["userInfo"], key = "#name + '-' + #userFetchType.title")
    @Transactional(readOnly = true)
    fun getByName(
        name: String,
        userFetchType: UserFetchType,
    ): GetUserDto =
        userRepository.findByName(name)?.let { user -> GetUserDto.from(user, userFetchType) }
            ?: throw NoSuchElementException("Failed to fetch user by name: $name")

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
                ?: throw NoSuchElementException("Failed to fetch user by id: $id")

        user.update(
            totalCommitCount = dto.totalCommitCount,
            followers = userRepository.findAllByNameIn(dto.followerNames),
            following = userRepository.findAllByNameIn(dto.followingNames),
        )

        userRepository.save(user)
    }

    fun changeCommitmon(
        id: Long,
        commitmon: Commitmon,
    ) {
        val user =
            userRepository.findByIdOrNull(id)
                ?: throw NoSuchElementException("Failed to fetch user by id: $id")

        user.changeCommitmon(commitmon)

        userRepository.save(user)

        clearCache(user.name)
    }

    private fun clearCache(name: String) {
        val cache = cacheManager.getCache("userInfo")
        UserFetchType.entries.forEach { userFetchType ->
            cache?.evictIfPresent(name + '-' + userFetchType.title)
        }
    }
}
