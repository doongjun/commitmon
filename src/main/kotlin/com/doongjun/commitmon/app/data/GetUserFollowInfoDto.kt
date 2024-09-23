package com.doongjun.commitmon.app.data

import com.doongjun.commitmon.core.NoArgs

@NoArgs
data class GetUserFollowInfoDto(
    val followerGithubIds: List<Long>,
    val followingGithubIds: List<Long>,
)
