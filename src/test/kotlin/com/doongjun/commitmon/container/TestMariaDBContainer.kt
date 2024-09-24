package com.doongjun.commitmon.container

import org.testcontainers.containers.MariaDBContainer

class TestMariaDBContainer : MariaDBContainer<TestMariaDBContainer>("mariadb:11.5.2") {
    companion object {
        private lateinit var container: TestMariaDBContainer

        fun getInstance(): TestMariaDBContainer {
            if (!Companion::container.isInitialized) {
                container = TestMariaDBContainer()
            }
            return container
        }
    }

    override fun start() {
        super.start()
        System.setProperty("DB_URL", container.jdbcUrl)
        System.setProperty("DB_USERNAME", container.username)
        System.setProperty("DB_PASSWORD", container.password)
    }

    override fun stop() {
        // do nothing, JVM handles shut down
    }
}
