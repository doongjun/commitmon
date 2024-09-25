package com.doongjun.commitmon.domain

import java.util.concurrent.ThreadLocalRandom

enum class Commitmon(
    val level: CommitmonLevel,
    val path: List<Commitmon> = emptyList(),
    val assetName: String,
    val nameKo: String,
    val isFlying: Boolean = false,
) {
    EGG(CommitmonLevel.EGG, assetName = "egg", nameKo = "알"),

    BOTAMON(CommitmonLevel.BABY, listOf(EGG), assetName = "botamon", nameKo = "깜몬"),
    KOROMON(CommitmonLevel.IN_TRAINING, listOf(EGG, BOTAMON), assetName = "koromon", nameKo = "코로몬"),
    AGUMON(CommitmonLevel.ROOKIE, listOf(EGG, BOTAMON, KOROMON), assetName = "agumon", nameKo = "아구몬"),
    GREYMON(CommitmonLevel.CHAMPION, listOf(EGG, BOTAMON, KOROMON, AGUMON), assetName = "greymon", nameKo = "그레이몬"),
    METALGREYMON(CommitmonLevel.PERFECT, listOf(EGG, BOTAMON, KOROMON, AGUMON, GREYMON), assetName = "metalGreymon", nameKo = "메탈그레이몬"),
    WARGREYMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, BOTAMON, KOROMON, AGUMON, GREYMON, METALGREYMON),
        assetName = "wargreymon",
        nameKo = "워그레이몬",
    ),

    PUNIMON(CommitmonLevel.BABY, listOf(EGG), assetName = "punimon", nameKo = "푸니몬"),
    TSUNOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, PUNIMON), assetName = "tsunomon", nameKo = "뿔몬"),
    GABUMON(CommitmonLevel.ROOKIE, listOf(EGG, PUNIMON, TSUNOMON), assetName = "gabumon", nameKo = "가루몬"),
    GARURUMON(CommitmonLevel.CHAMPION, listOf(EGG, PUNIMON, TSUNOMON, GABUMON), assetName = "garurumon", nameKo = "가루몬"),
    WEREGARURUMON(CommitmonLevel.PERFECT, listOf(EGG, PUNIMON, TSUNOMON, GABUMON, GARURUMON), assetName = "weregarurumon", nameKo = "워가루몬"),
    METALGARURUMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, PUNIMON, TSUNOMON, GABUMON, GARURUMON, WEREGARURUMON),
        assetName = "metalgarurumon",
        nameKo = "메탈가루몬",
    ),

    NYOKIMON(CommitmonLevel.BABY, listOf(EGG), assetName = "nyokimon", nameKo = "새싹몬"),
    PYOKOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, NYOKIMON), assetName = "pyokomon", nameKo = "어니몬"),
    PIYOMON(CommitmonLevel.ROOKIE, listOf(EGG, NYOKIMON, PYOKOMON), assetName = "piyomon", nameKo = "피요몬", true),
    BIRDRAMON(CommitmonLevel.CHAMPION, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON), assetName = "birdramon", nameKo = "버드라몬", true),
    GARUDAMON(CommitmonLevel.PERFECT, listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON), assetName = "garudamon", nameKo = "가루다몬", true),
    PHOENIXMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON, GARUDAMON),
        assetName = "phoenixmon",
        nameKo = "피닉스몬",
        true,
    ),
    ;

    companion object {
        private val random = ThreadLocalRandom.current()
        private val COMMITMON_MAP = entries.groupBy { it.level }

        fun randomCommitmon(level: CommitmonLevel): Commitmon =
            COMMITMON_MAP[level]!!.let {
                it[random.nextInt(it.size)]
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
                }.let { it[random.nextInt(it.size)] }
    }
}
