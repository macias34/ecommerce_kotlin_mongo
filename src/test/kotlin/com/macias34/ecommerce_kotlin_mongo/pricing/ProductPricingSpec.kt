package com.macias34.ecommerce_kotlin_mongo.pricing

import com.macias34.ecommerce_kotlin_mongo.DateRange
import com.macias34.ecommerce_kotlin_mongo.TestFixtures.Companion.money
import com.macias34.ecommerce_kotlin_mongo.Vendor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.Instant
import java.util.UUID

class ProductPricingSpec : FunSpec({

    val fixedDate = Instant.now()
    val fixedDateRange = DateRange(fixedDate, fixedDate.plus(Duration.ofDays(1)))

    test("Calculating the price with no active policies") {
        // Given
        var pricing = ProductPricing(UUID.randomUUID(), money(100), listOf(), listOf())
        var pricingContext = PricingContext(Vendor.ALLEGRO, fixedDate)

        // Then
        pricing.priceFor(pricingContext) shouldBe money(100)
    }

    test("A single Price Adjustment is applied") {
        // Given
        var priceAdjustment =
            PricingPolicy(Adjustment.ofPercentage(15.0), Applicability(Vendor.ALLEGRO, fixedDateRange))
        var pricing = ProductPricing(
            UUID.randomUUID(), money(100), listOf(priceAdjustment),
            listOf()
        )
        var pricingContext = PricingContext(Vendor.ALLEGRO, fixedDate)

        // Then
        pricing.priceFor(pricingContext) shouldBe money(115)
    }
})