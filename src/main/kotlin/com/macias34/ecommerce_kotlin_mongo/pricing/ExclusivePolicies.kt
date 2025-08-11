package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money

data class ExclusivePolicies(private val exclusivePolicies: Set<PricingPolicy>) {
    fun apply(currentPrice: Money, pricingContext: PricingContext): Money {
        var lowestPrice = currentPrice

        for (policy in exclusivePolicies) {
            val priceAfterApplyingPolicy = policy.apply(currentPrice, pricingContext)
            if (priceAfterApplyingPolicy.isLessThan(lowestPrice)) {
                lowestPrice = priceAfterApplyingPolicy
            }
        }

        return lowestPrice
    }
}
