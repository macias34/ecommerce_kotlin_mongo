package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money

interface PriceCalculationStep {
    fun apply(priceBeforeApplyingPolicies: Money, pricingContext: PricingContext): Money
}