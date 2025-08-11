package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money

data class CumulativePolicies(private val cumulativePolicies: Set<PricingPolicy>) : PriceCalculationStep {

    override fun apply(priceBeforeApplyingPolicies: Money, pricingContext: PricingContext): Money {
        return cumulativePolicies.fold(priceBeforeApplyingPolicies) {
            currentPrice, policy -> policy.apply(currentPrice, pricingContext)
        }
    }
}