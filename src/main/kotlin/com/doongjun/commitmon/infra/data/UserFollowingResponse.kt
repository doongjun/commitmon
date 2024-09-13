package com.doongjun.commitmon.infra.data

data class UserFollowingResponse(
    val user: User?,
) {
    data class User(
        val following: FollowInfo,
    )
}
