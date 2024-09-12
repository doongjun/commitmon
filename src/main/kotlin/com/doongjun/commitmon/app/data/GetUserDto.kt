package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.domain.User
import java.time.Instant

data class GetUserDto(
    val id: Long,
    val githubId: Long,
    val name: String,
    val createdDate: Instant,
    val lastModifiedDate: Instant,
) {
    companion object {
        fun from(user: User): GetUserDto =
            GetUserDto(
                id = user.id,
                githubId = user.githubId,
                name = user.name,
                createdDate = user.createdDate,
                lastModifiedDate = user.lastModifiedDate,
            )
    }
}
