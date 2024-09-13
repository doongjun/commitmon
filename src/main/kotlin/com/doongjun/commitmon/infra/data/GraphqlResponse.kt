package com.doongjun.commitmon.infra.data

data class GraphqlResponse<T>(
    val data: T,
    val errors: List<GraphqlError>?,
)

data class GraphqlError(
    val message: String,
    val type: String,
)
