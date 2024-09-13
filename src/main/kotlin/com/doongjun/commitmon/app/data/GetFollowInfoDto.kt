package com.doongjun.commitmon.app.data

data class GetFollowInfoDto(
    val followerGithubIds: List<Long>,
    val followingGithubIds: List<Long>,
)
