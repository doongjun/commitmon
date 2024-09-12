package com.doongjun.commitmon.app.data

data class CreateOrUpdateUserDto(
    val githubId: Long,
    val name: String,
    val totalCommitCount: Long,
    val followerGithubIds: List<Long>,
    val followingGithubIds: List<Long>,
)
