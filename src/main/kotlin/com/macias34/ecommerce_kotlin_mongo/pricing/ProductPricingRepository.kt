package com.macias34.ecommerce_kotlin_mongo.pricing

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface ProductPricingRepository : MongoRepository<ProductPricing, UUID> {
}