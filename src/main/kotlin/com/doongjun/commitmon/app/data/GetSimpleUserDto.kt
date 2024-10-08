package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.core.NoArgs
import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.User

@NoArgs
data class GetSimpleUserDto(
    val id: Long,
    val name: String,
    val totalCommitCount: Long,
    val commitmon: Commitmon,
    val exp: Int = 0,
) {
    companion object {
        fun from(user: User): GetSimpleUserDto =
            GetSimpleUserDto(
                id = user.id,
                name = user.name,
                totalCommitCount = user.totalCommitCount,
                commitmon = user.commitmon,
                exp = user.exp,
            )
    }
}
