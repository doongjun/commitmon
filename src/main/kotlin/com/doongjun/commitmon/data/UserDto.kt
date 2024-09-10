package com.doongjun.commitmon.data

import com.doongjun.commitmon.domain.User
import java.time.Instant

data class UserDto(
    val id: Long,
    val githubId: Long,
    val name: String,
    val createdDate: Instant,
    val lastModifiedDate: Instant,
) {
    companion object {
        fun from(user: User): UserDto =
            UserDto(
                id = user.id,
                githubId = user.githubId,
                name = user.name,
                createdDate = user.createdDate,
                lastModifiedDate = user.lastModifiedDate,
            )
    }
}
