package com.doongjun.commitmon.app.data

data class UpdateUserDto(
    val totalCommitCount: Long,
    val followerNames: List<String>,
    val followingNames: List<String>,
)
