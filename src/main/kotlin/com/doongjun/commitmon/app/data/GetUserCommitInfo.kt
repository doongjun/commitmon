package com.doongjun.commitmon.app.data

data class GetUserCommitInfo(
    val githubId: Long,
    val totalCommitCount: Long,
)
