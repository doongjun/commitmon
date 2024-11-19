package com.doongjun.commitmon.api.data

import com.doongjun.commitmon.app.data.GetSimpleUserDto
import com.doongjun.commitmon.domain.Commitmon

data class MeResponse(
    val id: Long,
    val name: String,
    val totalCommitCount: Long,
    val commitmon: Commitmon,
    val exp: Int = 0,
) {
    companion object {
        fun from(user: GetSimpleUserDto): MeResponse =
            MeResponse(
                id = user.id,
                name = user.name,
                totalCommitCount = user.totalCommitCount,
                commitmon = user.commitmon,
                exp = user.exp,
            )
    }
}
