package com.doongjun.commitmon.app.data

data class CreateUserDto(
    val name: String,
    val totalCommitCount: Long,
    val followerNames: List<String>,
    val followingNames: List<String>,
)
