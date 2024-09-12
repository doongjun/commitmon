package com.doongjun.commitmon.infra.data

data class UserFollowInfoVariables(
    val login: String,
    val first: Int,
    val after: String? = null,
)
