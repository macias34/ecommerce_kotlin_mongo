package com.macias34.ecommerce_kotlin_mongo

import io.mongock.runner.springboot.EnableMongock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableMongock
@EnableScheduling
@SpringBootApplication
class EcommerceKotlinMongoApplication

fun main(args: Array<String>) {
	runApplication<EcommerceKotlinMongoApplication>(*args)
}
