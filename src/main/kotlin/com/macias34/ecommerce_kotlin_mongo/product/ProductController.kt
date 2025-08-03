package com.macias34.ecommerce_kotlin_mongo.product

import com.macias34.ecommerce_kotlin_mongo.Vendor
import com.macias34.ecommerce_kotlin_mongo.toData
import org.javamoney.moneta.Money
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/products")
class ProductController(private val productRepository: ProductRepository) {

    @GetMapping("/{productId}")
    fun getProductData(@PathVariable("productId") productId: UUID): ProductData {
        val product = productRepository.findByIdOrNull(productId)
            ?: throw NotFoundException()

        val productData = ProductData(
            product.id,
            product.name,
            Vendor.ALLEGRO,
            Money.of(100, "PLN").toData(),
            100
        )
        return productData;
    }

    @PostMapping
    fun createProduct(@RequestBody createProduct: CreateProduct): ResponseEntity<ProductCreatedResponse> {
        val ( name ) = createProduct

        val product = Product(UUID.randomUUID(), name)
        productRepository.save(product)

        return ResponseEntity.ok(ProductCreatedResponse(product.id));
    }

}

