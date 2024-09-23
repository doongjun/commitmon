package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.app.data.UpdateUserDto
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
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
    fun get(id: Long): GetUserDto =
        userRepository.findByIdOrNull(id)?.let { user -> GetUserDto.from(user) }
            ?: throw IllegalArgumentException("Failed to fetch user by id: $id")

    fun create(dto: CreateUserDto): Long {
        val user =
            userRepository.save(
                User(
                    githubId = dto.githubId,
                    name = dto.name,
                    totalCommitCount = dto.totalCommitCount,
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

        val followers = userRepository.findAllByGithubIdIn(dto.followerGithubIds)
        val following = userRepository.findAllByGithubIdIn(dto.followingGithubIds)

        user.update(
            name = dto.name,
            totalCommitCount = dto.totalCommitCount,
            followers = followers,
            following = following,
        )

        userRepository.save(user)
    }
}
