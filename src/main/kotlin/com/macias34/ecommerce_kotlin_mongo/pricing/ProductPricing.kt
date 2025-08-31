package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product_pricing")
class ProductPricing private constructor(
    @Id val id: UUID, val productId: UUID, private val basePrice: Money, private val cumulativePolicies: CumulativePolicies,
    private val exclusivePolicies: ExclusivePolicies
) {
    fun priceFor(pricingContext: PricingContext): Money {
        val calculationSteps = listOf(cumulativePolicies, exclusivePolicies)

        val finalPrice = calculationSteps.fold(basePrice) {
            currentPrice, step -> step.apply(currentPrice, pricingContext)
        }

        return maxOf(finalPrice, Money.zero(finalPrice.currency))
    }

    companion object {
        fun of(
            productId: UUID,
            basePrice: Money, priceAdjustments: Set<PricingPolicy> = setOf(),
            exclusivePolicies: Set<PricingPolicy> = setOf()
        ): ProductPricing {

            return ProductPricing(
                UUID.randomUUID(),
                productId,
                basePrice,
                CumulativePolicies(priceAdjustments),
                ExclusivePolicies(exclusivePolicies)
            )
        }
    }
}