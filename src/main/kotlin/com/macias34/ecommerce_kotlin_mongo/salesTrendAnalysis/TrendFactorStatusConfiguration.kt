package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

data class TrendFactorStatusConfiguration(private val trendFactorStatusItems: List<TrendFactorStatusItem>) {
    fun statusOf(trendFactorValue: TrendFactorValue): TrendFactorStatus {
        val trendFactorStatusItem = trendFactorStatusItems.find { item -> item.range.contains(trendFactorValue) }
            ?: throw IllegalArgumentException("Status could not be found for given trend factor")
        return trendFactorStatusItem.status
    }
}
