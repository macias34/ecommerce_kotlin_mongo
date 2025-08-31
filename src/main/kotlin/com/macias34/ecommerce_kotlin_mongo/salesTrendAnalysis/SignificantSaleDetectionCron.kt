package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import com.macias34.ecommerce_kotlin_mongo.common.DateRange
import com.macias34.ecommerce_kotlin_mongo.common.EventPublisher
import org.slf4j.LoggerFactory
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

    companion object {
        private val logger = LoggerFactory.getLogger(SignificantSaleDetectionCron::class.java)
    }

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

            val currentSales = saleRepository
                .countByProductIdAndCapturedAtBetween(productId, now, now.plusSeconds(10L))

            logger.info("baseSales: $baseSales, currentSales: $currentSales")

            if (baseSales == 0L || currentSales == 0L) {
                return
            }

            val baseSalesRate = SalesRate.of(baseSales, Duration.ofSeconds(10))
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