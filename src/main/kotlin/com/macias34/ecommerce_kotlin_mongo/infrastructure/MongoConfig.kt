package com.macias34.ecommerce_kotlin_mongo.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfig {

    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        return MongoCustomConversions(
            listOf(
                MoneyWriteConverter(),
                MoneyReadConverter()
            )
        )
    }
}