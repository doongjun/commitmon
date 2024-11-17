package com.doongjun.commitmon.extension

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun Any.convertToString(): String {
    return jacksonObjectMapper().writeValueAsString(this)
}

fun <T> String.convertToObject(): T {
    val typeReference = object : TypeReference<T>() {}
    return jacksonObjectMapper().readValue(this, typeReference)
}
