package com.doongjun.commitmon.extension

inline infix fun <reified E : Enum<E>, V> ((E) -> V).findBy(value: V): E? = enumValues<E>().firstOrNull { this(it) == value }
