package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

data class TrendFactorStatusConfiguration(private val trendFactorStatusItems: List<TrendFactorStatusItem>) {
    companion object {
        fun default(): TrendFactorStatusConfiguration {
            return TrendFactorStatusConfiguration(
                listOf(
                    TrendFactorStatusItem(TrendFactorStatus.UNDERPERFORMING, TrendFactorRange.of(0.0, 1.0)),
                    TrendFactorStatusItem(TrendFactorStatus.STABLE, TrendFactorRange.of(1.0, 2.0)),
                    TrendFactorStatusItem(TrendFactorStatus.TRENDING, TrendFactorRange.fromToInfinite(2.0))
                ))
        }
    }

    fun statusOf(trendFactorValue: TrendFactorValue): TrendFactorStatus {
        val trendFactorStatusItem = trendFactorStatusItems.find { item -> item.range.contains(trendFactorValue) }
            ?: throw IllegalArgumentException("Status could not be found for given trend factor")
        return trendFactorStatusItem.status
    }
}
