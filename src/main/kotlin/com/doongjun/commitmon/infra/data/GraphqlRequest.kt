package com.doongjun.commitmon.infra.data

data class GraphqlRequest<T>(
    val query: String,
    val variables: T,
)
