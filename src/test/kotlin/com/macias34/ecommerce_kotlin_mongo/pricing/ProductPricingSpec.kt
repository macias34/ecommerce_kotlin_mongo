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
    val applicableContext = PricingContext(Vendor.ALLEGRO, fixedDate)
    val notApplicableContext = PricingContext(Vendor.AMAZON, fixedDate)
    val fixedApplicability = Applicability(Vendor.ALLEGRO, fixedDateRange)

    test("Calculating the price with no active policies") {
        // Given
        val pricing = ProductPricing.of(money(100))

        // Then
        pricing.priceFor(applicableContext) shouldBe money(100)
    }

    test("A single Price Adjustment is applied") {
        // Given
        val priceAdjustment =
            PricingPolicy.ofPercentage(15.0, fixedApplicability)
        val pricing = ProductPricing.of(
            money(100), setOf(priceAdjustment)
        )

        // Then
        pricing.priceFor(applicableContext) shouldBe money(115)
    }

    test("A Price Adjustment is not applied for not applicable context") {
        // Given
        val priceAdjustment =
            PricingPolicy.ofPercentage(15.0, fixedApplicability)
        val pricing = ProductPricing.of(
            money(100), setOf(priceAdjustment)
        )

        // Then
        pricing.priceFor(notApplicableContext) shouldBe money(100)
    }

    test("Multiple Price Adjustments are cumulative") {
        // Given
        val percentagePriceAdjustment =
            PricingPolicy.ofPercentage(10.0, fixedApplicability)
        val valuePriceAdjustment =
            PricingPolicy.ofValue(20.0, fixedApplicability)
        val pricing = ProductPricing.of(money(100),
            setOf(percentagePriceAdjustment, valuePriceAdjustment))

        // Then
        pricing.priceFor(applicableContext) shouldBe money(130)
    }

    test("A single Exclusive Policy is applied") {
        // Given
        val exclusivePolicy = PricingPolicy.ofPercentage(-25.0, fixedApplicability)
        val pricing = ProductPricing.of(money(200),
            exclusivePolicies = setOf(exclusivePolicy))

        // Then
        pricing.priceFor(applicableContext) shouldBe money(150)
    }

    test("An Exclusive Policy is not applied for not applicable context") {
        // Given
        val exclusivePolicy = PricingPolicy.ofPercentage(-25.0, fixedApplicability)
        val pricing = ProductPricing.of(money(200),
            exclusivePolicies = setOf(exclusivePolicy))

        // Then
        pricing.priceFor(notApplicableContext) shouldBe money(200)
    }

    test("The lowest price option is chosen from multiple Exclusive Policies") {
        // Given
        val higherFinalPricePolicy = PricingPolicy.ofPercentage(-20.0, fixedApplicability)
        val lowerFinalPricePolicy = PricingPolicy.ofValue(-25.0, fixedApplicability)
        val pricing = ProductPricing.of(money(100),
            exclusivePolicies = setOf(higherFinalPricePolicy, lowerFinalPricePolicy))

        // Then
        pricing.priceFor(applicableContext) shouldBe money(75)
    }
})