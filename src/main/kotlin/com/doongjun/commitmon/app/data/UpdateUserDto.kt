package com.doongjun.commitmon.app.data

data class UpdateUserDto(
    val name: String,
    val totalCommitCount: Long,
    val followerGithubIds: List<Long>,
    val followingGithubIds: List<Long>,
)
