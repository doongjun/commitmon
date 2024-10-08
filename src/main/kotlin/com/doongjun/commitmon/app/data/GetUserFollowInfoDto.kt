package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.core.NoArgs

@NoArgs
data class GetUserFollowInfoDto(
    val followerNames: List<String>,
    val followingNames: List<String>,
)
