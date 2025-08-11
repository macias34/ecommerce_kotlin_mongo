package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money

data class ExclusivePolicies(private val exclusivePolicies: Set<PricingPolicy>): PriceCalculationStep {
    override fun apply(priceBeforeApplyingPolicies: Money, pricingContext: PricingContext): Money {
        return exclusivePolicies.fold(priceBeforeApplyingPolicies) { lowestPriceSoFar, policy ->
            val priceFromCurrentPolicy = policy.apply(priceBeforeApplyingPolicies, pricingContext)

            if (priceFromCurrentPolicy.isLessThan(lowestPriceSoFar)) {
                priceFromCurrentPolicy
            } else {
                lowestPriceSoFar
            }
        }
    }
}
