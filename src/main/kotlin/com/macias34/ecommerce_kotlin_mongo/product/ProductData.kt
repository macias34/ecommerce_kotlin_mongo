package com.macias34.ecommerce_kotlin_mongo.product

import java.util.UUID

data class ProductData(
    val id: UUID,
    val name: String
)