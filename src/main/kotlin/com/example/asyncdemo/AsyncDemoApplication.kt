package com.example.asyncdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableFeignClients
@EnableAsync
class AsyncDemoApplication

fun main(args: Array<String>) {
	runApplication<AsyncDemoApplication>(*args)
}
