package com.doongjun.commitmon.infra.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCommitTotalCountResponse(
    @JsonProperty("total_count")
    val totalCount: Long,
)
