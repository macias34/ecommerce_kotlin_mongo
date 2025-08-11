package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product_pricing")
class ProductPricing private constructor(
    @Id val id: UUID, private val basePrice: Money, private val cumulativePolicies: CumulativePolicies,
    private val exclusivePolicies: ExclusivePolicies
) {
    fun priceFor(pricingContext: PricingContext): Money {
        val calculationSteps = listOf(cumulativePolicies, exclusivePolicies)

        return calculationSteps.fold(basePrice) {
            currentPrice, step -> step.apply(currentPrice, pricingContext)
        }
    }

    companion object {
        fun of(
            basePrice: Money, priceAdjustments: Set<PricingPolicy> = setOf(),
            exclusivePolicies: Set<PricingPolicy> = setOf()
        ): ProductPricing {

            return ProductPricing(
                UUID.randomUUID(),
                basePrice,
                CumulativePolicies(priceAdjustments),
                ExclusivePolicies(exclusivePolicies)
            )
        }
    }
}