package com.doongjun.commitmon.domain

enum class CommitmonLevel(
    val exp: Long,
    val order: Int,
) {
    EGG(0, 0),
    BABY(100, 1),
    IN_TRAINING(200, 2),
    ROOKIE(400, 3),
    CHAMPION(800, 4),
    ULTIMATE(1600, 5),
    ;

    companion object {
        fun fromExp(exp: Long): CommitmonLevel = entries.last { exp >= it.exp }
    }
}
