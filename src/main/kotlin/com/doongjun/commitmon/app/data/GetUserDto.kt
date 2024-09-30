package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.User

data class GetUserDto(
    val id: Long,
    val githubId: Long,
    val name: String,
    val totalCommitCount: Long,
    val commitmon: Commitmon,
    val exp: Int = 0,
    val followers: List<GetSimpleUserDto>,
    val following: List<GetSimpleUserDto>,
    val mutualFollowers: List<GetSimpleUserDto>,
) {
    fun toSimple(): GetSimpleUserDto =
        GetSimpleUserDto(
            id = id,
            githubId = githubId,
            name = name,
            totalCommitCount = totalCommitCount,
            commitmon = commitmon,
            exp = exp,
        )

    companion object {
        fun from(user: User): GetUserDto =
            GetUserDto(
                id = user.id,
                githubId = user.githubId,
                name = user.name,
                totalCommitCount = user.totalCommitCount,
                commitmon = user.commitmon,
                exp = user.exp,
                followers = user.followers.map { GetSimpleUserDto.from(it) },
                following = user.following.map { GetSimpleUserDto.from(it) },
                mutualFollowers = user.mutualFollowers.map { GetSimpleUserDto.from(it) },
            )
    }
}
