package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

class Fixtures {
    companion object {
        val fixedTrendFactorConfiguration = TrendFactorStatusConfiguration(
            listOf(
                TrendFactorStatusItem(TrendFactorStatus.UNDERPERFORMING, TrendFactorRange.of(0.0, 1.0)),
                TrendFactorStatusItem(TrendFactorStatus.STABLE, TrendFactorRange.of(1.0, 2.0)),
                TrendFactorStatusItem(TrendFactorStatus.TRENDING, TrendFactorRange.fromToInfinite(2.0))
            )
        )
    }
}