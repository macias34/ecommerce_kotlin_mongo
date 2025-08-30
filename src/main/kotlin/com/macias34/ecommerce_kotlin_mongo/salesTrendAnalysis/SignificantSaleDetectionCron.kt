package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import com.macias34.ecommerce_kotlin_mongo.DateRange
import com.macias34.ecommerce_kotlin_mongo.EventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Component
class SignificantSaleDetectionCron(
    private val salesTrendAnalysisService: SalesTrendAnalysisService,
    private val saleRepository: SaleRepository,
    private val eventPublisher: EventPublisher
) {

    @Scheduled(cron = "*/10 * * * * *")
    fun detectSignificantTrend() {
        val productIds = listOf(UUID.randomUUID(), UUID.randomUUID())

        for (productId in productIds) {
            val now = Instant.now()
            val dayStart = now.truncatedTo(ChronoUnit.DAYS)

            val baseSales = saleRepository
                .countByProductIdAndCapturedAtBetween(
                    productId,
                    dayStart.minus(1, ChronoUnit.DAYS),
                    dayStart
                )
            val baseSalesRate = SalesRate.of(baseSales, Duration.ofSeconds(10))

            val currentSales = saleRepository
                .countByProductIdAndCapturedAtBetween(productId, now, now.plusSeconds(10L))
            val currentSalesRate = SalesRate.of(currentSales, Duration.ofSeconds(10))

            val significantSalesActivity =
                salesTrendAnalysisService.detectSignificantSalesActivity(
                    baseSalesRate,
                    currentSalesRate,
                    TrendFactorStatusConfiguration.default()
                )

            eventPublisher.publish(
                SignificantSalesActivityDetected(
                    productId, DateRange(now, now.plusSeconds(10)),
                    significantSalesActivity.value.value, significantSalesActivity.status
                )
            )

        }

    }
}