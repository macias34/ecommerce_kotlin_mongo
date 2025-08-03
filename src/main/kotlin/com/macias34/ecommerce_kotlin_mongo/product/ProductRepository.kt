package com.macias34.ecommerce_kotlin_mongo.product

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface ProductRepository : MongoRepository<Product, UUID> {
}