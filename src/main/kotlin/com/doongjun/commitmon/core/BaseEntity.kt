package com.doongjun.commitmon.core

import com.github.f4b6a3.tsid.TsidCreator
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import org.hibernate.proxy.HibernateProxy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant
import java.util.Objects

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity : Persistable<Long> {
    @Id
    @Column(name = "id")
    private val id: Long = TsidCreator.getTsid().toLong()

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdDate: Instant = Instant.now()
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var lastModifiedDate: Instant = Instant.now()
        protected set

    @Transient
    private var isNew = true

    override fun getId() = id

    override fun isNew() = isNew

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is HibernateProxy && this::class != other::class) {
            return false
        }

        return id == getIdentifier(other)
    }

    private fun getIdentifier(obj: Any): Serializable =
        if (obj is HibernateProxy) {
            obj.hibernateLazyInitializer.identifier as Serializable
        } else {
            (obj as BaseEntity).id
        }

    override fun hashCode() = Objects.hashCode(id)

    @PostPersist
    @PostLoad
    protected fun load() {
        isNew = false
    }
}
