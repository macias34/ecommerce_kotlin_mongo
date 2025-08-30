package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration

class TrendFactorSpec : FunSpec({

    test("Positive trend detection") {
        // Given
        val baseSalesRate = SalesRate.of(10, Duration.ofHours(1))
        val currentSalesRate = SalesRate.of(25, Duration.ofHours(1))

        // When
        val trendFactor = TrendFactor.calculate(baseSalesRate, currentSalesRate,        TrendFactorStatusConfiguration.default())

        // Then
        trendFactor shouldBe TrendFactor.of(2.5, TrendFactorStatus.TRENDING)
    }

    test("Negative trend detection") {
        // Given
        val baseSalesRate = SalesRate.of(20, Duration.ofHours(1))
        val currentSalesRate = SalesRate.of(8, Duration.ofHours(1))

        // When
        val trendFactor = TrendFactor.calculate(baseSalesRate, currentSalesRate,        TrendFactorStatusConfiguration.default())

        // Then
        trendFactor shouldBe TrendFactor.of(0.4, TrendFactorStatus.UNDERPERFORMING)
    }

    test("Sales stop for a previously active product") {
        // Given
        val baseSalesRate = SalesRate.of(20, Duration.ofHours(1))
        val currentSalesRate = SalesRate.of(0, Duration.ofHours(1))

        // When
        val trendFactor = TrendFactor.calculate(baseSalesRate, currentSalesRate,        TrendFactorStatusConfiguration.default())

        // Then
        trendFactor shouldBe TrendFactor.of(0.0, TrendFactorStatus.UNDERPERFORMING)
    }
})