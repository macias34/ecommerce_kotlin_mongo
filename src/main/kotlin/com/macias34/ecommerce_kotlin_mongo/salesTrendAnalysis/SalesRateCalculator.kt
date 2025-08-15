package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import com.macias34.ecommerce_kotlin_mongo.DateRange
import java.time.Clock
import java.time.Duration
import java.time.Instant

class SalesRateCalculator(private val clock: Clock) {

    fun calculate(sales: List<Sale>, duration: Duration): SalesRate {
        val now = Instant.now(clock)
        val range = DateRange( now.minus(duration), now)

        var salesRateValue = 0L
        for (sale in sales) {
            if(range.contains(sale.capturedAt)){
                salesRateValue += sale.quantity.value
            }
        }

        return SalesRate.of(salesRateValue, duration)
    }
}