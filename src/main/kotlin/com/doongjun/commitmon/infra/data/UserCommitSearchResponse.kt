package com.doongjun.commitmon.infra.data

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCommitSearchResponse(
    @JsonProperty("total_count")
    val totalCount: Long,
    val items: List<Item>,
) {
    data class Item(
        val author: Author,
    ) {
        data class Author(
            val id: Long,
            val login: String,
        )
    }
}
