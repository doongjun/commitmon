package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.User
import java.time.Instant

data class GetUserDto(
    val id: Long,
    val githubId: Long,
    val name: String,
    val totalCommitCount: Long,
    val commitmon: Commitmon,
    val followers: List<GetSimpleUserDto>,
    val following: List<GetSimpleUserDto>,
    val mutualFollowers: List<GetSimpleUserDto>,
    val createdDate: Instant,
    val lastModifiedDate: Instant,
) {
    fun toSimple(): GetSimpleUserDto =
        GetSimpleUserDto(
            id = id,
            githubId = githubId,
            name = name,
            totalCommitCount = totalCommitCount,
            commitmon = commitmon,
        )

    companion object {
        fun from(user: User): GetUserDto =
            GetUserDto(
                id = user.id,
                githubId = user.githubId,
                name = user.name,
                totalCommitCount = user.totalCommitCount,
                commitmon = user.commitmon,
                followers = user.followers.map { GetSimpleUserDto.from(it) },
                following = user.following.map { GetSimpleUserDto.from(it) },
                mutualFollowers = user.mutualFollowers.map { GetSimpleUserDto.from(it) },
                createdDate = user.createdDate,
                lastModifiedDate = user.lastModifiedDate,
            )
    }
}
