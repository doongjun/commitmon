package com.doongjun.commitmon.domain

enum class CommitmonLevel(
    val exp: Long,
    val order: Int,
) {
    EGG(0, 0), // 알
    BABY(100, 1), // 유아기
    IN_TRAINING(200, 2), // 유년기
    ROOKIE(400, 3), // 성장기
    CHAMPION(800, 4), // 성숙기
    PERFECT(1600, 5), // 완전체
    ULTIMATE(3200, 6), // 궁극체
    ;

    fun nextLevel(): CommitmonLevel = entries.firstOrNull { it.order == this.order + 1 } ?: this

    companion object {
        fun fromExp(exp: Long): CommitmonLevel = entries.last { exp >= it.exp }
    }
}
