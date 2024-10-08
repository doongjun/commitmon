package com.doongjun.commitmon.infra.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCommitSearchResponse(
    @JsonProperty("total_count")
    val totalCount: Long,
)
