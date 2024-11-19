package com.doongjun.commitmon.domain

import java.util.concurrent.ThreadLocalRandom

enum class Commitmon(
    val level: CommitmonLevel,
    val path: List<Commitmon> = emptyList(),
    val assetName: String,
    val nameKo: String,
    val seed: Int? = null,
    val isFlying: Boolean = false,
) {
    EGG(CommitmonLevel.EGG, assetName = "egg", nameKo = "알"),

    BOTAMON(CommitmonLevel.BABY, listOf(EGG), assetName = "botamon", nameKo = "깜몬", seed = 1),
    KOROMON(CommitmonLevel.IN_TRAINING, listOf(EGG, BOTAMON), assetName = "koromon", nameKo = "코로몬", seed = 1),
    AGUMON(CommitmonLevel.ROOKIE, listOf(EGG, BOTAMON, KOROMON), assetName = "agumon", nameKo = "아구몬", seed = 1),
    GREYMON(CommitmonLevel.CHAMPION, listOf(EGG, BOTAMON, KOROMON, AGUMON), assetName = "greymon", nameKo = "그레이몬", seed = 1),
    METALGREYMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, BOTAMON, KOROMON, AGUMON, GREYMON),
        assetName = "metalgreymon",
        nameKo = "메탈그레이몬",
        seed = 1,
    ),
    WARGREYMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, BOTAMON, KOROMON, AGUMON, GREYMON, METALGREYMON),
        assetName = "wargreymon",
        nameKo = "워그레이몬",
        seed = 1,
    ),

    PUNIMON(CommitmonLevel.BABY, listOf(EGG), assetName = "punimon", nameKo = "푸니몬", seed = 2),
    TSUNOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, PUNIMON), assetName = "tsunomon", nameKo = "뿔몬", seed = 2),
    GABUMON(CommitmonLevel.ROOKIE, listOf(EGG, PUNIMON, TSUNOMON), assetName = "gabumon", nameKo = "파피몬", seed = 2),
    GARURUMON(CommitmonLevel.CHAMPION, listOf(EGG, PUNIMON, TSUNOMON, GABUMON), assetName = "garurumon", nameKo = "가루몬", seed = 2),
    WEREGARURUMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, PUNIMON, TSUNOMON, GABUMON, GARURUMON),
        assetName = "weregarurumon",
        nameKo = "워가루몬",
        seed = 2,
    ),
    METALGARURUMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, PUNIMON, TSUNOMON, GABUMON, GARURUMON, WEREGARURUMON),
        assetName = "metalgarurumon",
        nameKo = "메탈가루몬",
        seed = 2,
    ),

    NYOKIMON(CommitmonLevel.BABY, listOf(EGG), assetName = "nyokimon", nameKo = "새싹몬", seed = 3),
    PYOKOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, NYOKIMON), assetName = "pyokomon", nameKo = "어니몬", seed = 3),
    PIYOMON(CommitmonLevel.ROOKIE, listOf(EGG, NYOKIMON, PYOKOMON), assetName = "piyomon", nameKo = "피요몬", seed = 3, isFlying = true),
    BIRDRAMON(
        CommitmonLevel.CHAMPION,
        listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON),
        assetName = "birdramon",
        nameKo = "버드라몬",
        seed = 3,
        isFlying = true,
    ),
    GARUDAMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON),
        assetName = "garudamon",
        nameKo = "가루다몬",
        seed = 3,
        isFlying = true,
    ),
    PHOENIXMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, NYOKIMON, PYOKOMON, PIYOMON, BIRDRAMON, GARUDAMON),
        assetName = "phoenixmon",
        nameKo = "피닉스몬",
        seed = 3,
        isFlying = true,
    ),

    BUBBMON(CommitmonLevel.BABY, listOf(EGG), assetName = "bubbmon", nameKo = "뽀글몬", seed = 4),
    MOTIMON(CommitmonLevel.IN_TRAINING, listOf(EGG, BUBBMON), assetName = "motimon", nameKo = "모티몬", seed = 4),
    TENTOMON(CommitmonLevel.ROOKIE, listOf(EGG, BUBBMON, MOTIMON), assetName = "tentomon", nameKo = "텐타몬", seed = 4),
    KABUTERIMON(CommitmonLevel.CHAMPION, listOf(EGG, BUBBMON, MOTIMON, TENTOMON), assetName = "kabuterimon", nameKo = "캅테리몬", seed = 4),
    ATLURKABUTERIMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, BUBBMON, MOTIMON, TENTOMON, KABUTERIMON),
        assetName = "atlurkabuterimon",
        nameKo = "아트라캅테리몬",
        seed = 4,
    ),
    HERCULESKABUTERIMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, BUBBMON, MOTIMON, TENTOMON, KABUTERIMON, ATLURKABUTERIMON),
        assetName = "herculeskabuterimon",
        nameKo = "헤라클레스캅테리몬",
        seed = 4,
    ),

    POYOMON(CommitmonLevel.BABY, listOf(EGG), assetName = "poyomon", nameKo = "포요몬", seed = 5),
    TOKOMON(CommitmonLevel.IN_TRAINING, listOf(EGG, POYOMON), assetName = "tokomon", nameKo = "토코몬", seed = 5),
    PATAMON(CommitmonLevel.ROOKIE, listOf(EGG, POYOMON, TOKOMON), assetName = "patamon", nameKo = "파닥몬", seed = 5, isFlying = true),
    ANGEMON(CommitmonLevel.CHAMPION, listOf(EGG, POYOMON, TOKOMON, PATAMON), assetName = "angemon", nameKo = "엔젤몬", seed = 5),
    HOLYANGEMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, POYOMON, TOKOMON, PATAMON, ANGEMON),
        assetName = "holyangemon",
        nameKo = "홀리엔젤몬",
        seed = 5,
    ),
    SERAPHIMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, POYOMON, TOKOMON, PATAMON, ANGEMON, HOLYANGEMON),
        assetName = "seraphimon",
        nameKo = "세라피몬",
        seed = 5,
        isFlying = true,
    ),

    YURAMON(CommitmonLevel.BABY, listOf(EGG), assetName = "yuramon", nameKo = "유라몬", seed = 6),
    TANEMON(CommitmonLevel.IN_TRAINING, listOf(EGG, YURAMON), assetName = "tanemon", nameKo = "시드몬", seed = 6),
    PALMON(CommitmonLevel.ROOKIE, listOf(EGG, YURAMON, TANEMON), assetName = "palmon", nameKo = "팔몬", seed = 6),
    TOGEMON(CommitmonLevel.CHAMPION, listOf(EGG, YURAMON, TANEMON, PALMON), assetName = "togemon", nameKo = "니드몬", seed = 6),
    LILYMON(CommitmonLevel.PERFECT, listOf(EGG, YURAMON, TANEMON, PALMON, TOGEMON), assetName = "lilymon", nameKo = "릴리몬", seed = 6),
    ROSEMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, YURAMON, TANEMON, PALMON, TOGEMON, LILYMON),
        assetName = "rosemon",
        nameKo = "로제몬",
        seed = 6,
    ),

    PICHIMON(CommitmonLevel.BABY, listOf(EGG), assetName = "pichimon", nameKo = "피치몬", seed = 7),
    PUKAMON(CommitmonLevel.IN_TRAINING, listOf(EGG, PICHIMON), assetName = "pukamon", nameKo = "둥실몬", seed = 7),
    GOMAMON(CommitmonLevel.ROOKIE, listOf(EGG, PICHIMON, PUKAMON), assetName = "gomamon", nameKo = "쉬라몬", seed = 7),
    IKKAKUMON(CommitmonLevel.CHAMPION, listOf(EGG, PICHIMON, PUKAMON, GOMAMON), assetName = "ikkakumon", nameKo = "원뿔몬", seed = 7),
    ZUDOMON(CommitmonLevel.PERFECT, listOf(EGG, PICHIMON, PUKAMON, GOMAMON, IKKAKUMON), assetName = "zudomon", nameKo = "쥬드몬", seed = 7),
    VIKEMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, PICHIMON, PUKAMON, GOMAMON, IKKAKUMON, ZUDOMON),
        assetName = "vikemon",
        nameKo = "바이킹몬",
        seed = 7,
    ),

    SNOWBOTAMON(CommitmonLevel.BABY, listOf(EGG), assetName = "snowbotamon", nameKo = "하얀몬", seed = 8),
    NYAROMON(CommitmonLevel.IN_TRAINING, listOf(EGG, SNOWBOTAMON), assetName = "nyaromon", nameKo = "야옹몬", seed = 8),
    SALAMON(CommitmonLevel.ROOKIE, listOf(EGG, SNOWBOTAMON, NYAROMON), assetName = "salamon", nameKo = "플롯트몬", seed = 8),
    GATOMON(CommitmonLevel.CHAMPION, listOf(EGG, SNOWBOTAMON, NYAROMON, SALAMON), assetName = "gatomon", nameKo = "가트몬", seed = 8),
    ANGEWOMON(
        CommitmonLevel.PERFECT,
        listOf(EGG, SNOWBOTAMON, NYAROMON, SALAMON, GATOMON),
        assetName = "angewomon",
        nameKo = "엔젤우몬",
        seed = 8,
    ),
    HOLYDRAMON(
        CommitmonLevel.ULTIMATE,
        listOf(EGG, SNOWBOTAMON, NYAROMON, SALAMON, GATOMON, ANGEWOMON),
        assetName = "holydramon",
        nameKo = "홀리드라몬",
        seed = 8,
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
                    if (commitmon.seed != null) it.seed == commitmon.seed else true
                }.apply {
                    if (isEmpty()) return commitmon
                }.let { it[random.nextInt(it.size)] }
    }
}
