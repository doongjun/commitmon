package com.doongjun.commitmon.infra.data

data class UserFollowInfoResponse(
    val data: Data,
) {
    data class Data(
        val user: User,
    ) {
        data class User(
            val followers: UserFollowersResponse.Data.User.Followers,
            val following: UserFollowingResponse.Data.User.Following,
        )
    }
}
