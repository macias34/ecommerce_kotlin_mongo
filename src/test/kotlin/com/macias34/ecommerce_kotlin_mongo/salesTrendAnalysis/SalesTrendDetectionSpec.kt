package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis.Fixtures.Companion.fixedTrendFactorConfiguration
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration

class SalesTrendDetectionSpec: FunSpec({
    val salesTrendAnalysisService = SalesTrendAnalysisService()

    test("Positive trend detection") {
        // Given
        val baseSalesRate = SalesRate.of(10, Duration.ofHours(1))
        val currentSalesRate = SalesRate.of(25, Duration.ofHours(1))

        // When
        val significantSalesActivity =
            salesTrendAnalysisService.detectSignificantSalesActivity(baseSalesRate, currentSalesRate, fixedTrendFactorConfiguration)

        // Then
        significantSalesActivity shouldBe SignificantSalesActivity.of(2.5, TrendFactorStatus.TRENDING)
    }

    test("Negative trend detection") {
        // Given
        val baseSalesRate = SalesRate.of(20, Duration.ofHours(1))
        val currentSalesRate = SalesRate.of(8, Duration.ofHours(1))

        // When
        val significantSalesActivity =
            salesTrendAnalysisService.detectSignificantSalesActivity(baseSalesRate, currentSalesRate,
                fixedTrendFactorConfiguration)

        // Then
        significantSalesActivity shouldBe SignificantSalesActivity.of(0.4, TrendFactorStatus.UNDERPERFORMING)
    }

    test("Insignificant sales activity change is ignored") {
        // Given
        val significantActivityThreshold = SignificantActivityThreshold(0.2)
        val baseSalesRate = SalesRate.of(10, Duration.ofHours(1))
        val currentSalesRate = SalesRate.of(11, Duration.ofHours(1))

        // When
        val significantSalesActivity =
            salesTrendAnalysisService.detectSignificantSalesActivity(baseSalesRate, currentSalesRate,
                fixedTrendFactorConfiguration, significantActivityThreshold)

        // Then
        significantSalesActivity shouldBe null
    }
})