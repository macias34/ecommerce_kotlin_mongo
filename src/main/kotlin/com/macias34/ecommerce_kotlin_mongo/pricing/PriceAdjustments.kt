package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money

data class PriceAdjustments(private val priceAdjustments: Set<PricingPolicy>) : PriceCalculationStep {

    override fun apply(currentPrice: Money, pricingContext: PricingContext): Money {
        var newPrice = currentPrice
        for (policy in priceAdjustments) {
            newPrice = policy.apply(newPrice, pricingContext)
        }
        return newPrice
    }
}