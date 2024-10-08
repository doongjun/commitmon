package com.doongjun.commitmon.infra.data

data class UserFollowInfoResponse(
    val user: User?,
) {
    data class User(
        val followers: FollowInfo,
        val following: FollowInfo,
    )
}

data class FollowInfo(
    val totalCount: Int,
    val pageInfo: FollowPageInfo,
    val nodes: List<FollowNode>,
)

data class FollowPageInfo(
    val hasNextPage: Boolean,
    val endCursor: String,
)

data class FollowNode(
    val login: String,
)
