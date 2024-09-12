package com.doongjun.commitmon.app

import com.doongjun.commitmon.app.data.CreateOrUpdateUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.domain.User
import com.doongjun.commitmon.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = true)
    fun existsByName(name: String) = userRepository.existsByName(name)

    fun createOrUpdate(request: CreateOrUpdateUserDto): GetUserDto {
        val followers = userRepository.findAllByGithubIdIn(request.followerGithubIds)
        val following = userRepository.findAllByGithubIdIn(request.followingGithubIds)

        val user =
            userRepository.findByGithubId(request.githubId)?.apply {
                update(
                    name = request.name,
                    totalCommitCount = request.totalCommitCount,
                    followers = followers,
                    following = following,
                )
            } ?: User(
                githubId = request.githubId,
                name = request.name,
                totalCommitCount = request.totalCommitCount,
                followers = followers,
                following = following,
            )

        return GetUserDto.from(userRepository.save(user))
    }
}
