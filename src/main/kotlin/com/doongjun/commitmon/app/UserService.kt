package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateUserDto
import com.doongjun.commitmon.app.data.GetSimpleUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.app.data.PatchUserDto
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

    @Cacheable(value = ["userInfo"], key = "#githubId + '-' + #userFetchType.title")
    @Transactional(readOnly = true)
    fun getByGithubId(
        githubId: Long,
        userFetchType: UserFetchType,
    ): GetUserDto =
        userRepository.findByGithubId(githubId)?.let { user -> GetUserDto.from(user, userFetchType) }
            ?: throw IllegalArgumentException("Failed to fetch user by githubId: $githubId")

    @Transactional(readOnly = true)
    fun getSimpleByGithubId(githubId: Long): GetSimpleUserDto =
        userRepository.findByGithubId(githubId)?.let { user -> GetSimpleUserDto.from(user) }
            ?: throw IllegalArgumentException("Failed to fetch user by githubId: $githubId")

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

        user.patch(
            name = dto.name,
            totalCommitCount = dto.totalCommitCount,
            followers = followers,
            following = following,
        )

        userRepository.save(user)
    }

    fun patch(
        id: Long,
        dto: PatchUserDto,
    ) {
        val user =
            userRepository.findByIdOrNull(id)
                ?: throw IllegalArgumentException("Failed to fetch user by id: $id")

        val followers = dto.followerGithubIds?.let { userRepository.findAllByGithubIdIn(it) }
        val following = dto.followingGithubIds?.let { userRepository.findAllByGithubIdIn(it) }

        user.patch(
            name = dto.name,
            totalCommitCount = dto.totalCommitCount,
            followers = followers,
            following = following,
        )

        userRepository.save(user)
    }
}
