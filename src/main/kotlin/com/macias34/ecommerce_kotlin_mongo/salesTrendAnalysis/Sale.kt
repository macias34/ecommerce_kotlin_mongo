package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.UUID

@Document(collection = "sale")
data class Sale private constructor(@Id val id: UUID, val productId: UUID, val quantity: Quantity, val capturedAt: Instant,
    val analyzedAt: Instant? = null
    ) {
    companion object {
        fun of(productId: UUID, quantity: Long, capturedAt: Instant): Sale {
            return Sale(UUID.randomUUID(), productId, Quantity(quantity), capturedAt)
        }
    }

    fun markAsAnalyzed(): Sale {
        return this.copy(analyzedAt = Instant.now())
    }
}

data class Quantity(val value: Long) {
    init {
        require(value > 0)
    }
}
