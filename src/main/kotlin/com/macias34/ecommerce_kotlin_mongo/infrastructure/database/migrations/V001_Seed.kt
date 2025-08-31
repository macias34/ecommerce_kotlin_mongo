package com.macias34.ecommerce_kotlin_mongo.infrastructure.database.migrations


import com.macias34.ecommerce_kotlin_mongo.common.DateRange
import com.macias34.ecommerce_kotlin_mongo.common.Vendor
import com.macias34.ecommerce_kotlin_mongo.pricing.Applicability
import com.macias34.ecommerce_kotlin_mongo.pricing.PricingPolicy
import com.macias34.ecommerce_kotlin_mongo.pricing.ProductPricing
import com.macias34.ecommerce_kotlin_mongo.pricing.ProductPricingRepository
import com.macias34.ecommerce_kotlin_mongo.product.Product
import com.macias34.ecommerce_kotlin_mongo.product.ProductRepository
import io.mongock.api.annotations.*
import org.javamoney.moneta.Money
import java.time.Duration
import java.time.Instant
import java.util.UUID

@ChangeUnit(id = "initial-seed", order = "001", author = "maciej.radzimirski")
class V001_Seed {

    val iphoneId = UUID.fromString("78dc4fee-7fe4-4e29-a964-d4f639ff17e3")
    val fixedDate = Instant.now()
    val fixedDateRange = DateRange(fixedDate, fixedDate.plus(Duration.ofDays(1_000)))
    val fixedApplicability = Applicability(Vendor.ALLEGRO, fixedDateRange)

    @Execution
    fun execution(productRepository: ProductRepository, productPricingRepository: ProductPricingRepository) {
        seedProducts(productRepository)
        seedProductPricing(productPricingRepository)
    }

    @RollbackExecution
    fun rollback(){
        TODO()
    }

    private fun seedProducts(productRepository: ProductRepository){
        val iphone = Product(iphoneId, "Iphone")
        productRepository.save(iphone)
    }

    private fun seedProductPricing(productPricingRepository: ProductPricingRepository){
        val iphonePricing = complexPricing(iphoneId)
        productPricingRepository.save(iphonePricing)
    }

    private fun complexPricing(productId: UUID): ProductPricing {
        val cumulativePolicy = PricingPolicy.ofPercentage(10.0, fixedApplicability)
        val percentageExclusivePolicy = PricingPolicy.ofPercentage(-20.0, fixedApplicability)
        val valueExclusivePolicy = PricingPolicy.ofValue(-25.0, fixedApplicability)
        return ProductPricing.of(
            productId,
            Money.of(100, "PLN"),
            setOf(cumulativePolicy), setOf(percentageExclusivePolicy, valueExclusivePolicy))
    }
}