package com.doongjun.commitmon.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@EnableAsync
@Configuration
class AsyncConfig {
    @Bean
    fun taskExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.setThreadNamePrefix("async-executor-")
        executor.corePoolSize = POOL_SIZE
        executor.maxPoolSize = POOL_SIZE * 2
        executor.queueCapacity = QUEUE_SIZE
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.initialize()
        return executor
    }

    companion object {
        private const val POOL_SIZE = 3
        private const val QUEUE_SIZE = 3
    }
}
