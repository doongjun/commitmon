package com.doongjun.commitmon.app

import com.doongjun.commitmon.container.TestMariaDBContainer
import com.doongjun.commitmon.container.TestRedisContainer
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@ActiveProfiles("test")
@Testcontainers
@Transactional
@SpringBootTest
abstract class BaseAppTest {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    protected fun clear() {
        entityManager.flush()
        entityManager.clear()
    }

    companion object {
        @Container
        val mariaDBContainer = TestMariaDBContainer.getInstance()

        @Container
        val redisContainer = TestRedisContainer.getInstance()
    }
}
