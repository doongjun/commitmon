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

    BUBBMON(CommitmonLevel.BABY, listOf(EGG), assetName = "bubbmon", nameKo = "뽀글몬"),
    MOTIMON(CommitmonLevel.IN_TRAINING, listOf(EGG, BUBBMON), assetName = "motimon", nameKo = "모티몬"),
    TENTOMON(CommitmonLevel.ROOKIE, listOf(EGG, BUBBMON, MOTIMON), assetName = "tentomon", nameKo = "텐타몬"),
    KABUTERIMON(CommitmonLevel.CHAMPION, listOf(EGG, BUBBMON, MOTIMON, TENTOMON), assetName = "kabuterimon", nameKo = "캅테리몬"),
    ATLURKABUTERIMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, BUBBMON, MOTIMON, TENTOMON, KABUTERIMON),
        assetName = "atlurkabuterimon",
        nameKo = "아트라캅테리몬",
    ),
    HERCULESKABUTERIMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, BUBBMON, MOTIMON, TENTOMON, KABUTERIMON, ATLURKABUTERIMON),
        assetName = "herculeskabuterimon",
        nameKo = "헤라클레스캅테리몬",
    ),

    POYOMON(CommitmonLevel.BABY, listOf(EGG), assetName = "poyomon", nameKo = "포요몬"),
    TOKOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, POYOMON), assetName = "tokomon", nameKo = "토코몬"),
    PATAMON(CommitmonLevel.ROOKIE, listOf(EGG, POYOMON, TOKOMON), assetName = "patamon", nameKo = "파닥몬"),
    ANGEMON(CommitmonLevel.CHAMPION, listOf(EGG, POYOMON, TOKOMON, PATAMON), assetName = "angemon", nameKo = "엔젤몬"),
    HOLYANGEMON(CommitmonLevel.PERFECT, listOf(EGG, POYOMON, TOKOMON, PATAMON, ANGEMON), assetName = "holyangemon", nameKo = "홀리엔젤몬"),
    SERAPHIMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, POYOMON, TOKOMON, PATAMON, ANGEMON, HOLYANGEMON),
        assetName = "seraphimon",
        nameKo = "세라피몬",
    ),

    YURAMON(CommitmonLevel.BABY, listOf(EGG), assetName = "yuramon", nameKo = "유라몬"),
    TANEMON(CommitmonLevel.IN_TRAINING, listOf(EGG, YURAMON), assetName = "tanemon", nameKo = "시드몬"),
    PALMON(CommitmonLevel.ROOKIE, listOf(EGG, YURAMON, TANEMON), assetName = "palmon", nameKo = "팔몬"),
    TOGEMON(CommitmonLevel.CHAMPION, listOf(EGG, YURAMON, TANEMON, PALMON), assetName = "togemon", nameKo = "니드몬"),
    LILYMON(CommitmonLevel.PERFECT, listOf(EGG, YURAMON, TANEMON, PALMON, TOGEMON), assetName = "lilymon", nameKo = "릴리몬"),
    ROSEMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, YURAMON, TANEMON, PALMON, TOGEMON, LILYMON),
        assetName = "rosemon",
        nameKo = "로제몬",
    ),

    PICHIMON(CommitmonLevel.BABY, listOf(EGG), assetName = "pichimon", nameKo = "피치몬"),
    PUKAMON(CommitmonLevel.IN_TRAINING, listOf(EGG, PICHIMON), assetName = "pukamon", nameKo = "둥실몬"),
    GOMAMON(CommitmonLevel.ROOKIE, listOf(EGG, PICHIMON, PUKAMON), assetName = "gomamon", nameKo = "쉬라몬"),
    IKKAKUMON(CommitmonLevel.CHAMPION, listOf(EGG, PICHIMON, PUKAMON, GOMAMON), assetName = "ikkakumon", nameKo = "원뿔몬"),
    ZUDOMON(CommitmonLevel.PERFECT, listOf(EGG, PICHIMON, PUKAMON, GOMAMON, IKKAKUMON), assetName = "zudomon", nameKo = "쥬드몬"),
    VIKEMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, PICHIMON, PUKAMON, GOMAMON, IKKAKUMON, ZUDOMON),
        assetName = "vikemon",
        nameKo = "바이킹몬",
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
