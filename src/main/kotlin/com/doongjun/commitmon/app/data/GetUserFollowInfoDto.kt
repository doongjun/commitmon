package com.doongjun.commitmon.app.data

data class GetUserFollowInfoDto(
    val followerGithubIds: List<Long>,
    val followingGithubIds: List<Long>,
)
