package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration

class SalesTrendDetectionSpec : FunSpec({
    val trendFactorConfiguration = TrendFactorStatusConfiguration(
        listOf(
            TrendFactorStatusItem(TrendFactorStatus.UNDERPERFORMING, TrendFactorRange.of(0.0, 1.0)),
            TrendFactorStatusItem(TrendFactorStatus.STABLE, TrendFactorRange.of(1.0, 2.0)),
            TrendFactorStatusItem(TrendFactorStatus.TRENDING, TrendFactorRange.fromToInfinite(2.0))
        )
    )

    test("Positive trend detection") {
        // Given
        val baseSalesRate = SalesRate.of(10, Duration.ofHours(1))

        // When
        val currentSalesRate = SalesRate.of(25, Duration.ofHours(1))
        val trendFactor = TrendFactor.calculate(baseSalesRate, currentSalesRate, trendFactorConfiguration)

        // Then
        trendFactor shouldBe TrendFactor.of(2.5, TrendFactorStatus.TRENDING)
    }
})