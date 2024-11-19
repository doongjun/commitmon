package com.doongjun.commitmon.api.data

import com.doongjun.commitmon.domain.Commitmon
import jakarta.validation.constraints.NotNull

data class ChangeCommitmonRequest(
    @field:NotNull
    val commitmon: Commitmon?,
)
