package com.macias34.ecommerce_kotlin_mongo.product

import org.springframework.stereotype.Service

@Service
class ProductFacade(private val productRepository: ProductRepository) {
    fun getProducts(): List<ProductData> {
        return productRepository.findAll().map { product -> ProductData(product.id, product.name) }
    }
}