package com.doongjun.commitmon.api.data

import jakarta.validation.constraints.NotNull

data class RefreshTokenRequest(
    @field:NotNull
    val refreshToken: String?,
)
