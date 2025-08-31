package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.UUID

@RestController
@RequestMapping("/sales")
class SaleController(private val saleRepository: SaleRepository) {

    @GetMapping("/{productId}")
    fun sale(@PathVariable("productId") productId: UUID): Sale {
        return saleRepository.save(Sale.of(productId, 5, Instant.now()))
    }
}