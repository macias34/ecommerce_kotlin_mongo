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

    test("A single Cumulative Policy is applied") {
        // Given
        val cumulativePolicy =
            PricingPolicy.ofPercentage(15.0, fixedApplicability)
        val pricing = ProductPricing.of(
            money(100), setOf(cumulativePolicy)
        )

        // Then
        pricing.priceFor(applicableContext) shouldBe money(115)
    }

    test("A Cumulative Policy is not applied for not applicable context") {
        // Given
        val cumulativePolicy =
            PricingPolicy.ofPercentage(15.0, fixedApplicability)
        val pricing = ProductPricing.of(
            money(100), setOf(cumulativePolicy)
        )

        // Then
        pricing.priceFor(notApplicableContext) shouldBe money(100)
    }

    test("Multiple Cumulative Policies are cumulative") {
        // Given
        val percentagePolicy =
            PricingPolicy.ofPercentage(10.0, fixedApplicability)
        val valuePolicy =
            PricingPolicy.ofValue(20.0, fixedApplicability)
        val pricing = ProductPricing.of(money(100),
            setOf(percentagePolicy, valuePolicy))

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

    test("The best Exclusive Policy is applied after all Cumulative Policies") {
        // Given
        val cumulativePolicy = PricingPolicy.ofPercentage(10.0, fixedApplicability)
        val percentageExclusivePolicy = PricingPolicy.ofPercentage(-20.0, fixedApplicability)
        val valueExclusivePolicy = PricingPolicy.ofValue(-25.0, fixedApplicability)
        val pricing = ProductPricing.of(money(100),
            setOf(cumulativePolicy), setOf(percentageExclusivePolicy, valueExclusivePolicy))

        // Then
        pricing.priceFor(applicableContext) shouldBe money(85)
    }

    test("The final price cannot be negative") {
        // Given
        val exclusivePolicy =  PricingPolicy.ofValue(-15.0, fixedApplicability)
        val pricing = ProductPricing.of(money(10),
            exclusivePolicies = setOf(exclusivePolicy))

        // Then
        pricing.priceFor(applicableContext) shouldBe money(0)
    }
})