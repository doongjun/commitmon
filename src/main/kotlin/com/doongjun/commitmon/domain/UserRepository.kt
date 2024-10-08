package com.doongjun.commitmon.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): User?

    fun findAllByNameIn(names: List<String>): List<User>

    fun existsByName(name: String): Boolean
}
