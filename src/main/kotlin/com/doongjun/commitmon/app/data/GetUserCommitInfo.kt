package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.core.NoArgs
import java.io.Serializable

@NoArgs
data class GetUserCommitInfo(
    val totalCommitCount: Long,
) : Serializable
