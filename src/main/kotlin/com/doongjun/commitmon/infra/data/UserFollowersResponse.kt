package com.doongjun.commitmon.infra.data

data class UserFollowersResponse(
    val data: Data,
) {
    data class Data(
        val user: User,
    ) {
        data class User(
            val followers: Followers,
        ) {
            data class Followers(
                val totalCount: Int,
                val pageInfo: PageInfo,
                val nodes: List<Node>,
            ) {
                data class PageInfo(
                    val hasNextPage: Boolean,
                    val endCursor: String,
                )

                data class Node(
                    val login: String,
                    val databaseId: Long,
                )
            }
        }
    }
}
