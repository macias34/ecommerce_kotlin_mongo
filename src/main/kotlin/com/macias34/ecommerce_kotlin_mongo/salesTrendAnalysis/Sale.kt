package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import java.time.Instant
import java.util.UUID

data class Sale private constructor(val id: UUID, val productId: UUID, val quantity: Quantity, val capturedAt: Instant) {
    companion object {
        fun of(productId: UUID, quantity: Long, capturedAt: Instant): Sale {
            return Sale(UUID.randomUUID(), productId, Quantity(quantity), capturedAt)
        }
    }
}

data class Quantity(val value: Long) {
    init {
        require(value > 0)
    }
}
