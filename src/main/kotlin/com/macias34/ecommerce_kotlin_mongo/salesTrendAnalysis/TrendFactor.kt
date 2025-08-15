package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

data class TrendFactor(val value: TrendFactorValue, val status: TrendFactorStatus) {
    companion object {
        fun calculate(baseSalesRate: SalesRate, currentSalesRate: SalesRate, trendFactorConfiguration: TrendFactorStatusConfiguration): TrendFactor {
            val value = TrendFactorValue(currentSalesRate.value.value.toDouble() / baseSalesRate.value.value)
            val status = trendFactorConfiguration.statusOf(value)
            return TrendFactor(value, status)
        }

        fun of(value: Double, status: TrendFactorStatus): TrendFactor {
            return TrendFactor(TrendFactorValue(value), status)
        }
    }
}

data class TrendFactorValue(val value: Double){
    init {
        require(value >= 0)
    }
}