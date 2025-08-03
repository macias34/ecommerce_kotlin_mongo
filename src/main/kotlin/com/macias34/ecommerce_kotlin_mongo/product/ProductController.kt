package com.macias34.ecommerce_kotlin_mongo.product

import com.macias34.ecommerce_kotlin_mongo.Vendor
import com.macias34.ecommerce_kotlin_mongo.toData
import org.javamoney.moneta.Money
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductController {
    @GetMapping("/{productId}")
    fun getProductData(@PathVariable("productId") productId: UUID): ProductData {
        val productData = ProductData(
            productId,
            "Product",
            Vendor.ALLEGRO,
            Money.of(100, "PLN").toData(),
            100
        )
        return productData;
    }
}

