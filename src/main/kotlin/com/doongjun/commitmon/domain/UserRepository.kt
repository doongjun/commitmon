package com.doongjun.commitmon.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByGithubId(githubId: Long): User?

    fun findAllByGithubIdIn(githubIds: List<Long>): List<User>

    fun existsByName(name: String): Boolean
}
