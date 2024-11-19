package com.doongjun.commitmon.api.data

import com.doongjun.commitmon.app.data.GetSimpleUserDto
import com.doongjun.commitmon.app.data.GetUserDto
import com.doongjun.commitmon.domain.Commitmon

data class MeDetailResponse(
    val id: Long,
    val name: String,
    val totalCommitCount: Long,
    val commitmon: Commitmon,
    val exp: Int = 0,
    val followers: List<GetSimpleUserDto>,
    val following: List<GetSimpleUserDto>,
) {
    companion object {
        fun from(user: GetUserDto): MeDetailResponse =
            MeDetailResponse(
                id = user.id,
                name = user.name,
                totalCommitCount = user.totalCommitCount,
                commitmon = user.commitmon,
                exp = user.exp,
                followers = user.followers,
                following = user.following,
            )
    }
}
