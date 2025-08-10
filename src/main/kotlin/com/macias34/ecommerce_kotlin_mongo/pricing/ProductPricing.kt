package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product_pricing")
class ProductPricing private constructor(@Id val id: UUID, private val basePrice: Money, private val priceAdjustments: List<PricingPolicy>,
                                         private val policies: List<PricingPolicy>) {
    fun priceFor(pricingContext: PricingContext): Money {
        val applicablePriceAdjustments = priceAdjustments.filter { adjustment -> adjustment.isApplicable(pricingContext) }

        var finalPrice = basePrice
        applicablePriceAdjustments.forEach { adjustment -> finalPrice = adjustment.apply(finalPrice) }

        return finalPrice
    }

    companion object {
        fun of(basePrice: Money, priceAdjustments: List<PricingPolicy> = listOf(),
               policies: List<PricingPolicy> = listOf()
               ): ProductPricing {

            return ProductPricing(
                UUID.randomUUID(),
                basePrice,
                priceAdjustments,
                policies
            )
        }
    }
}