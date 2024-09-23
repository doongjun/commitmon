package com.doongjun.commitmon.app.data

data class PatchUserDto(
    val name: String? = null,
    val totalCommitCount: Long? = null,
    val followerGithubIds: List<Long>? = null,
    val followingGithubIds: List<Long>? = null,
)
