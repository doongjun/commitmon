package com.doongjun.commitmon.container

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class TestRedisContainer : GenericContainer<Nothing>(DockerImageName.parse("redis:7.4.0")) {
    companion object {
        private lateinit var container: TestRedisContainer

        fun getInstance(): TestRedisContainer {
            if (!Companion::container.isInitialized) {
                container = TestRedisContainer().withExposedPorts(6379)
            }
            return container
        }
    }

    override fun start() {
        super.start()
        System.setProperty("REDIS_HOST", container.host)
        System.setProperty("REDIS_PORT", container.getMappedPort(6379).toString())
    }

    override fun stop() {
        // do nothing, JVM handles shut down
    }
}
