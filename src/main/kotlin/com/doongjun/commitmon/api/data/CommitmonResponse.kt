package com.doongjun.commitmon.api.data

import com.doongjun.commitmon.domain.Commitmon
import com.doongjun.commitmon.domain.CommitmonLevel

data class CommitmonResponse(
    val commitmons: List<CommitmonData>,
) {
    data class CommitmonData(
        val commitmon: Commitmon,
        val name: String,
        val nameKo: String,
        val level: CommitmonLevel,
        val seed: Int,
        val imageUrl: String,
    )

    companion object {
        private val commitmons =
            Commitmon.entries.filter { it != Commitmon.EGG }.map {
                CommitmonData(
                    commitmon = it,
                    name = it.name.lowercase(),
                    nameKo = it.nameKo,
                    level = it.level,
                    seed = it.seed ?: 0,
                    imageUrl = "https://s3-commitmon.s3.ap-northeast-2.amazonaws.com/static/${it.assetName}.png",
                )
            }

        fun of(): CommitmonResponse = CommitmonResponse(commitmons)
    }
}
