package com.doongjun.commitmon.domain

import com.doongjun.commitmon.core.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "follow",
    uniqueConstraints = [
        UniqueConstraint(name = "UNQ_FOLLOW_FOLLOWER_FOLLOWING", columnNames = ["follower_id", "following_id"]),
    ],
    indexes = [
        Index(name = "IDX_FOLLOW_FOLLOWER_ID", columnList = "follower_id"),
        Index(name = "IDX_FOLLOW_FOLLOWING_ID", columnList = "following_id"),
    ],
)
class Follow(
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower_id")
    val follower: User,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_id")
    val following: User,
) : BaseEntity()
