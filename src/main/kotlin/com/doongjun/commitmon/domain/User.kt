package com.doongjun.commitmon.domain

import com.doongjun.commitmon.core.BaseEntity
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.Index
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "`user`", indexes = [Index(name = "IDX_USER_GITHUB_ID", columnList = "github_id", unique = true)])
class User(
    @Column(name = "github_id", unique = true, nullable = false)
    val githubId: Long,
    name: String,
    totalCommitCount: Long = 0,
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "total_commit_count", nullable = false)
    var totalCommitCount: Long = totalCommitCount
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "commitmon", nullable = false)
    var commitmon: Commitmon = Commitmon.randomCommitmon(CommitmonLevel.fromExp(totalCommitCount))
        protected set

    @OneToMany(mappedBy = "follower", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    protected val mutableFollowers: MutableSet<Follow> = mutableSetOf()
    val followers: List<User> get() = mutableFollowers.map { it.following }

    @OneToMany(mappedBy = "following", fetch = LAZY, cascade = [ALL], orphanRemoval = true)
    protected val mutableFollowing: MutableSet<Follow> = mutableSetOf()
    val following: List<User> get() = mutableFollowing.map { it.follower }

    val mutualFollowers: List<User> get() = following.filter { it in followers }

    val exp: Int get() {
        return if (commitmon.level == CommitmonLevel.ULTIMATE) {
            100
        } else {
            ((this.totalCommitCount - commitmon.level.exp).toDouble() / (commitmon.level.nextLevel().exp - commitmon.level.exp) * 100)
                .toInt()
        }
    }

    fun update(
        name: String,
        totalCommitCount: Long,
        followers: List<User>,
        following: List<User>,
    ) {
        this.name = name
        this.totalCommitCount = totalCommitCount
        evolveCommitmon(CommitmonLevel.fromExp(totalCommitCount))
        updateFollowers(followers)
        updateFollowing(following)
    }

    private fun evolveCommitmon(level: CommitmonLevel) {
        if (this.commitmon.level.order < level.order) {
            this.commitmon = Commitmon.randomLevelTreeCommitmon(level, this.commitmon)
        }
    }

    private fun updateFollowers(followers: List<User>) {
        val removals = this.followers - followers.toSet()
        val additions = followers - this.followers.toSet()
        mutableFollowers.removeAll { it.following in removals }
        mutableFollowers.addAll(toFollowers(additions))
    }

    private fun updateFollowing(following: List<User>) {
        val removals = this.following - following.toSet()
        val additions = following - this.following.toSet()
        mutableFollowing.removeAll { it.follower in removals }
        mutableFollowing.addAll(toFollowing(additions))
    }

    private fun toFollowers(followers: List<User>) = followers.map { Follow(follower = this, following = it) }.toMutableSet()

    private fun toFollowing(following: List<User>) = following.map { Follow(follower = it, following = this) }.toMutableSet()
}
