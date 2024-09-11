package com.doongjun.commitmon.infra.data

data class UserContributionsResponse(
    val data: Data,
) {
    data class Data(
        val user: User,
    ) {
        data class User(
            val contributionsCollection: ContributionsCollection,
            val databaseId: Long,
        ) {
            data class ContributionsCollection(
                val contributionCalendar: ContributionCalendar,
            ) {
                data class ContributionCalendar(
                    val totalContributions: Int,
                )
            }
        }
    }
}
