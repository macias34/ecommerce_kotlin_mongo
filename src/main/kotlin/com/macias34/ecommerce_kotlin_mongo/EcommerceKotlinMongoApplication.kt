package com.macias34.ecommerce_kotlin_mongo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class EcommerceKotlinMongoApplication

fun main(args: Array<String>) {
	runApplication<EcommerceKotlinMongoApplication>(*args)
}
