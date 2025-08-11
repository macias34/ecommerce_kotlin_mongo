package com.macias34.ecommerce_kotlin_mongo.pricing

import org.javamoney.moneta.Money

interface PriceCalculationStep {
    fun apply(currentPrice: Money, pricingContext: PricingContext): Money
}