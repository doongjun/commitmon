package com.doongjun.commitmon.domain

import com.doongjun.commitmon.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(name = "`user`", indexes = [Index(name = "IDX_USER_GITHUB_ID", columnList = "github_id", unique = true)])
class User(
    @Column(name = "github_id", unique = true, nullable = false)
    val githubId: Long,
    name: String,
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set
}
