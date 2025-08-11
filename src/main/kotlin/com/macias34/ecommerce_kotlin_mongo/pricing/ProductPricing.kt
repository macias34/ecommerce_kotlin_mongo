package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product_pricing")
class ProductPricing private constructor(
    @Id val id: UUID, private val basePrice: Money, private val priceAdjustments: List<PricingPolicy>,
    private val exclusivePolicies: List<PricingPolicy>
) {
    fun priceFor(pricingContext: PricingContext): Money {
        val applicablePriceAdjustments =
            priceAdjustments.filter { adjustment -> adjustment.isApplicable(pricingContext) }
        val applicableExclusivePolicies =
            exclusivePolicies.filter { adjustment -> adjustment.isApplicable(pricingContext) }

        var finalPrice = basePrice
        applicablePriceAdjustments.forEach { adjustment -> finalPrice = adjustment.apply(finalPrice) }
        finalPrice = priceAfterApplyingBestExclusivePolicy(finalPrice, applicableExclusivePolicies)

        return finalPrice
    }

    private fun priceAfterApplyingBestExclusivePolicy(
        finalPrice: Money,
        exclusivePolicies: List<PricingPolicy>
    ): Money {
        var lowestPrice = finalPrice

        for (policy in exclusivePolicies) {
            val priceAfterApplyingPolicy = policy.apply(finalPrice)
            if (priceAfterApplyingPolicy.isLessThan(lowestPrice)) {
                lowestPrice = priceAfterApplyingPolicy
            }
        }

        return lowestPrice
    }

    companion object {
        fun of(
            basePrice: Money, priceAdjustments: List<PricingPolicy> = listOf(),
            exclusivePolicies: List<PricingPolicy> = listOf()
        ): ProductPricing {

            return ProductPricing(
                UUID.randomUUID(),
                basePrice,
                priceAdjustments,
                exclusivePolicies
            )
        }
    }
}