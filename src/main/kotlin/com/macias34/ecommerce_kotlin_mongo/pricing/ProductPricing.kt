package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product_pricing")
class ProductPricing private constructor(
    @Id val id: UUID, private val basePrice: Money, private val priceAdjustments: PriceAdjustments,
    private val exclusivePolicies: ExclusivePolicies
) {
    fun priceFor(pricingContext: PricingContext): Money {
        var finalPrice = basePrice
        finalPrice = priceAdjustments.apply(finalPrice, pricingContext)
        finalPrice = exclusivePolicies.apply(finalPrice, pricingContext)

        return finalPrice
    }

    companion object {
        fun of(
            basePrice: Money, priceAdjustments: Set<PricingPolicy> = setOf(),
            exclusivePolicies: Set<PricingPolicy> = setOf()
        ): ProductPricing {

            return ProductPricing(
                UUID.randomUUID(),
                basePrice,
                PriceAdjustments(priceAdjustments),
                ExclusivePolicies(exclusivePolicies)
            )
        }
    }
}