package com.doongjun.commitmon.domain

import java.util.concurrent.ThreadLocalRandom

enum class Commitmon(
    val level: CommitmonLevel,
    val path: List<Commitmon> = emptyList(),
) {
    EGG(CommitmonLevel.EGG),

    BOATAMON(CommitmonLevel.BABY, listOf(EGG)),
    KOROMON(CommitmonLevel.IN_TRAINING, listOf(EGG, BOATAMON)),
    AGUMON(CommitmonLevel.ROOKIE, listOf(EGG, BOATAMON, KOROMON)),
    GREYMON(CommitmonLevel.CHAMPION, listOf(EGG, BOATAMON, KOROMON, AGUMON)),
    METALGREYMON(CommitmonLevel.PERFECT, listOf(EGG, BOATAMON, KOROMON, AGUMON, GREYMON)),
    WARGREYMON(CommitmonLevel.ULTIMATE, listOf(EGG, BOATAMON, KOROMON, AGUMON, GREYMON, METALGREYMON)),

    PUNIMON(CommitmonLevel.BABY, listOf(EGG)),
    TSUNOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, PUNIMON)),
    GABUMON(CommitmonLevel.ROOKIE, listOf(EGG, PUNIMON, TSUNOMON)),
    GARURUMON(CommitmonLevel.CHAMPION, listOf(EGG, PUNIMON, TSUNOMON, GABUMON)),
    WEREGARURUMON(CommitmonLevel.PERFECT, listOf(EGG, PUNIMON, TSUNOMON, GABUMON, GARURUMON)),
    METALGARURUMON(CommitmonLevel.ULTIMATE, listOf(EGG, PUNIMON, TSUNOMON, GABUMON, GARURUMON, WEREGARURUMON)),

    NYOKIMON(CommitmonLevel.BABY, listOf(EGG)),
    PYOKOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, NYOKIMON)),
    PIYOMON(CommitmonLevel.ROOKIE, listOf(EGG, NYOKIMON, PYOKOMON)),
    BIRDRAMON(CommitmonLevel.CHAMPION, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON)),
    GARUDAMON(CommitmonLevel.PERFECT, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON)),
    PHOENIXMON(CommitmonLevel.ULTIMATE, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON, GARUDAMON)),
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
