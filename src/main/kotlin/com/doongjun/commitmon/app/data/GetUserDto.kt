package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.app.UserFetchType
import com.doongjun.commitmon.core.NoArgs
import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.User

@NoArgs
data class GetUserDto(
    val id: Long,
    val name: String,
    val totalCommitCount: Long,
    val commitmon: Commitmon,
    val exp: Int = 0,
    val fetchedUsers: List<GetSimpleUserDto>,
    private val followerIds: List<Long>,
    private val followingIds: List<Long>,
) {
    val followers: List<GetSimpleUserDto> by lazy {
        fetchedUsers.filter { it.id in followerIds }
    }
    val following: List<GetSimpleUserDto> by lazy {
        fetchedUsers.filter { it.id in followingIds }
    }

    fun toSimple(): GetSimpleUserDto =
        GetSimpleUserDto(
            id = id,
            name = name,
            totalCommitCount = totalCommitCount,
            commitmon = commitmon,
            exp = exp,
        )

    companion object {
        fun from(
            user: User,
            userFetchType: UserFetchType,
        ): GetUserDto =
            GetUserDto(
                id = user.id,
                name = user.name,
                totalCommitCount = user.totalCommitCount,
                commitmon = user.commitmon,
                exp = user.exp,
                fetchedUsers =
                    when (userFetchType) {
                        UserFetchType.ALL ->
                            listOf(user.followers, user.following)
                                .flatten()
                                .distinct()
                                .map { GetSimpleUserDto.from(it) }
                        UserFetchType.MUTUAL ->
                            user.mutualFollowers
                                .map { GetSimpleUserDto.from(it) }
                        UserFetchType.SOLO -> emptyList()
                    },
                followerIds = user.followers.map { it.id },
                followingIds = user.following.map { it.id },
            )
    }
}
