package com.doongjun.commitmon.app

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class BaseAppTest {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    protected fun clear() {
        entityManager.flush()
        entityManager.clear()
    }
}
