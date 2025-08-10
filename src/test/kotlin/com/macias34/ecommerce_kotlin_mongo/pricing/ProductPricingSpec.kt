package com.macias34.ecommerce_kotlin_mongo.pricing

import com.macias34.ecommerce_kotlin_mongo.DateRange
import com.macias34.ecommerce_kotlin_mongo.TestFixtures.Companion.money
import com.macias34.ecommerce_kotlin_mongo.Vendor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.Instant

class ProductPricingSpec : FunSpec({

    val fixedDate = Instant.now()
    val fixedDateRange = DateRange(fixedDate, fixedDate.plus(Duration.ofDays(1)))

    test("Calculating the price with no active policies") {
        // Given
        val pricing = ProductPricing.of(money(100))
        val pricingContext = PricingContext(Vendor.ALLEGRO, fixedDate)

        // Then
        pricing.priceFor(pricingContext) shouldBe money(100)
    }

    test("A single Price Adjustment is applied") {
        // Given
        val priceAdjustment =
            PricingPolicy.ofPercentage(15.0, Applicability(Vendor.ALLEGRO, fixedDateRange))
        val pricing = ProductPricing.of(
            money(100), listOf(priceAdjustment)
        )
        val pricingContext = PricingContext(Vendor.ALLEGRO, fixedDate)

        // Then
        pricing.priceFor(pricingContext) shouldBe money(115)
    }

    test("Multiple Price Adjustments are cumulative") {
        // Given
        val percentagePriceAdjustment =
            PricingPolicy.ofPercentage(10.0, Applicability(Vendor.ALLEGRO, fixedDateRange))
        val valuePriceAdjustment =
            PricingPolicy.ofValue(20.0, Applicability(Vendor.ALLEGRO, fixedDateRange))
        val pricing = ProductPricing.of(money(100), listOf(percentagePriceAdjustment, valuePriceAdjustment))
        val pricingContext = PricingContext(Vendor.ALLEGRO, fixedDate)

        // Then
        pricing.priceFor(pricingContext) shouldBe money(130)
    }
})