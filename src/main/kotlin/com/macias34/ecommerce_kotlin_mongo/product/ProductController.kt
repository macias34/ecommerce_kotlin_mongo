package com.macias34.ecommerce_kotlin_mongo.product

import com.macias34.ecommerce_kotlin_mongo.pricing.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productRepository: ProductRepository,
    private val productPricingRepository: ProductPricingRepository
) {

//    @GetMapping("/{productId}")
//    fun getProductData(@PathVariable("productId") productId: UUID): ResponseEntity<ProductData> {
//        val product = productRepository.findByIdOrNull(productId)
//            ?: throw NotFoundException()
//
//        val productData = ProductData(
//            product.id,
//            product.name,
//            Vendor.ALLEGRO,
//            Money.of(100, "PLN").toData(),
//            100
//        )
//
//        return ResponseEntity.ok(productData);
//    }
//
//    @PostMapping
//    fun createProduct(@RequestBody createProduct: CreateProduct): ResponseEntity<ProductCreatedResponse> {
//        val ( name ) = createProduct
//
//        val product = Product(UUID.randomUUID(), name)
//        productRepository.save(product)
//
//        return ResponseEntity.ok(ProductCreatedResponse(product.id));
//    }
//
//    @PostMapping("/pricing")
//    fun createPricing(){
//        val now = Instant.now()
//        val pricingPolicy = PricingPolicy(Adjustment.ofPercentage(15.0, AdjustmentType.PERCENTAGE), Applicability(Vendor.ALLEGRO, DateRange(
//            now, now.plus(Duration.ofDays(7))
//        )))
//        val pricing = ProductPricing(UUID.randomUUID(), Money.of(100, "PLN"),
//            listOf(pricingPolicy))
//        productPricingRepository.save(pricing)
//    }

}

