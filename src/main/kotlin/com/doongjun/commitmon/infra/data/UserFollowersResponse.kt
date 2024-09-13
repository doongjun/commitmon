package com.doongjun.commitmon.infra.data

data class UserFollowersResponse(
    val user: User?,
) {
    data class User(
        val followers: FollowInfo,
    )
}
