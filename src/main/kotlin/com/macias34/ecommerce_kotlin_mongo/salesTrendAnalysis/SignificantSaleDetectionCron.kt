package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import com.macias34.ecommerce_kotlin_mongo.common.DateRange
import com.macias34.ecommerce_kotlin_mongo.common.EventPublisher
import com.macias34.ecommerce_kotlin_mongo.product.ProductData
import com.macias34.ecommerce_kotlin_mongo.product.ProductFacade
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class SignificantSaleDetectionCron(
    private val salesTrendAnalysisService: SalesTrendAnalysisService,
    private val saleRepository: SaleRepository,
    private val productFacade: ProductFacade,
    private val eventPublisher: EventPublisher
) {

    companion object {
        private val logger = LoggerFactory.getLogger(SignificantSaleDetectionCron::class.java)
    }

    @Scheduled(cron = "*/10 * * * * *")
    fun detectSignificantTrend() {
        val productIds = productFacade.getProducts().map(ProductData::id)
        for (productId in productIds) {
            val now = Instant.now()
            val dayStart = now.truncatedTo(ChronoUnit.DAYS)

            val baseSales = saleRepository
                .findByProductIdAndCapturedAtBetween(
                    productId,
                    dayStart.minus(1, ChronoUnit.DAYS),
                    dayStart
                )

            val currentSales = saleRepository
                .findByProductIdAndAnalyzedAtIsNull(productId)

            val baseSalesCount = if (baseSales.size.toLong() <= 0L ) 1L else baseSales.size.toLong()
            val currentSalesCount = currentSales.size.toLong()

            logger.info("baseSalesCount: $baseSalesCount, currentSalesCount: $currentSalesCount")

            val baseSalesRate = SalesRate.of(baseSalesCount, Duration.ofSeconds(10))
            val currentSalesRate = SalesRate.of(currentSalesCount, Duration.ofSeconds(10))

            val significantSalesActivity =
                salesTrendAnalysisService.detectSignificantSalesActivity(
                    baseSalesRate,
                    currentSalesRate,
                    TrendFactorStatusConfiguration.default()
                )

            val analyzedSales = currentSales.map { sale -> sale.markAsAnalyzed() }

            saleRepository.saveAll(analyzedSales)
            eventPublisher.publish(
                SignificantSalesActivityDetected(
                    productId, DateRange(now, now.plusSeconds(10)),
                    significantSalesActivity.value.value, significantSalesActivity.status
                )
            )
        }

    }
}