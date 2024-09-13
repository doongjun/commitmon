package com.doongjun.commitmon.domain

import java.util.concurrent.ThreadLocalRandom

enum class Commitmon(
    val level: CommitmonLevel,
    val path: List<Commitmon> = emptyList(),
) {
    EGG(CommitmonLevel.EGG),

    BOATAMON(CommitmonLevel.BABY, listOf(EGG)),
    AGUMON(CommitmonLevel.IN_TRAINING, listOf(EGG, BOATAMON)),
    GREYMON(CommitmonLevel.ROOKIE, listOf(EGG, BOATAMON, AGUMON)),
    METALGREYMON(CommitmonLevel.CHAMPION, listOf(EGG, BOATAMON, AGUMON, GREYMON)),
    WARGREYMON(CommitmonLevel.ULTIMATE, listOf(EGG, BOATAMON, AGUMON, GREYMON, METALGREYMON)),

    TSUNOMON(CommitmonLevel.BABY, listOf(EGG)),
    GABUMON(CommitmonLevel.IN_TRAINING, listOf(EGG, TSUNOMON)),
    GARURUMON(CommitmonLevel.ROOKIE, listOf(EGG, TSUNOMON, GABUMON)),
    WEREGARURUMON(CommitmonLevel.CHAMPION, listOf(EGG, TSUNOMON, GABUMON, GARURUMON)),
    METALGARURUMON(CommitmonLevel.ULTIMATE, listOf(EGG, TSUNOMON, GABUMON, GARURUMON, WEREGARURUMON)),

    NYOKIMON(CommitmonLevel.BABY, listOf(EGG)),
    PYOKOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, NYOKIMON)),
    PIYOMON(CommitmonLevel.ROOKIE, listOf(EGG, NYOKIMON, PYOKOMON)),
    BIRDRAMON(CommitmonLevel.CHAMPION, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON)),
    GARUDAMON(CommitmonLevel.ULTIMATE, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON)),
    ;

    companion object {
        private val COMMITMON_MAP = entries.groupBy { it.level }

        fun randomCommitmon(level: CommitmonLevel): Commitmon =
            COMMITMON_MAP[level]!!.let {
                it[ThreadLocalRandom.current().nextInt(it.size)]
            }

        fun randomLevelTreeCommitmon(
            level: CommitmonLevel,
            commitmon: Commitmon,
        ): Commitmon =
            COMMITMON_MAP[level]!!
                .filter {
                    it.path.containsAll(commitmon.path)
                }.apply {
                    if (isEmpty()) return commitmon
                }.let { it[ThreadLocalRandom.current().nextInt(it.size)] }
    }
}
