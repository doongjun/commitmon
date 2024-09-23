package com.doongjun.commitmon.app.data

import java.io.Serializable

data class GetUserCommitInfo(
    val githubId: Long,
    val totalCommitCount: Long,
) : Serializable
