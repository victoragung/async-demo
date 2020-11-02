package com.example.asyncdemo

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@TestConfiguration
@EnableAsync
class AsyncTestConfig {

    @Bean
    @Primary
    fun getExecutor(): TaskExecutor {

        val threadPoolTaskExecutorWithOneSecondDelay = ThreadPoolTaskExecutor()

        threadPoolTaskExecutorWithOneSecondDelay.setTaskDecorator { r ->
            Runnable {
                println("Wait 1 second before executing async task ...")
                Thread.sleep(1000)
                r.run()
            }
        }
        return threadPoolTaskExecutorWithOneSecondDelay
    }
}
