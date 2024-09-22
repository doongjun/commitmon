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
    val followers: List<SimpleUserDto>,
    val following: List<SimpleUserDto>,
    val mutualFollowers: List<SimpleUserDto>,
    val createdDate: Instant,
    val lastModifiedDate: Instant,
) {
    fun toSimple(): SimpleUserDto =
        SimpleUserDto(
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
                followers = user.followers.map { SimpleUserDto.from(it) },
                following = user.following.map { SimpleUserDto.from(it) },
                mutualFollowers = user.mutualFollowers.map { SimpleUserDto.from(it) },
                createdDate = user.createdDate,
                lastModifiedDate = user.lastModifiedDate,
            )
    }

    data class SimpleUserDto(
        val id: Long,
        val githubId: Long,
        val name: String,
        val totalCommitCount: Long,
        val commitmon: Commitmon,
    ) {
        companion object {
            fun from(user: User): SimpleUserDto =
                SimpleUserDto(
                    id = user.id,
                    githubId = user.githubId,
                    name = user.name,
                    totalCommitCount = user.totalCommitCount,
                    commitmon = user.commitmon,
                )
        }
    }
}
