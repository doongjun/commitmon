package com.doongjun.commitmon.app.data

data class CreateUserDto(
    val githubId: Long,
    val name: String,
    val totalCommitCount: Long,
)
