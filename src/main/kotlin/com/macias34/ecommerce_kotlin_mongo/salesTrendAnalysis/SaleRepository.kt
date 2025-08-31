package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant
import java.util.UUID

interface SaleRepository : MongoRepository<Sale, UUID> {
    fun findByProductIdAndAnalyzedAtIsNull(
        productId: UUID,
    ): List<Sale>

    fun findByProductIdAndCapturedAtBetween(
        productId: UUID,
        capturedAtAfter: Instant,
        capturedAtBefore: Instant
    ): List<Sale>


}