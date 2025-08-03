package com.macias34.ecommerce_kotlin_mongo.product

import lombok.EqualsAndHashCode
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "product")
class Product(
    @EqualsAndHashCode.Include
    @Id
    val id: UUID,
    val name: String
)