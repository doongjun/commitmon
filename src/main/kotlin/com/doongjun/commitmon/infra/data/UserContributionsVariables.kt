package com.doongjun.commitmon.infra.data

import java.time.Year

data class UserContributionsVariables(
    val login: String,
    val from: String,
    val to: String,
) {
    companion object {
        fun of(
            login: String,
            year: Year,
        ): UserContributionsVariables =
            UserContributionsVariables(
                login = login,
                from = "$year-01-01T00:00:00Z",
                to = "$year-12-31T23:59:59Z",
            )
    }
}
