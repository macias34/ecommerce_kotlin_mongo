package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

data class SignificantActivityThreshold(val value: Double) {
    fun isActivitySignificant(baseSalesRate: SalesRate, currentSalesRate: SalesRate): Boolean {
        val rangeStart = baseSalesRate.value.value - value
        val rangeEnd = baseSalesRate.value.value + value
        val currentValue = currentSalesRate.value.value

        return currentValue <= rangeStart && currentValue >= rangeEnd
    }

    init {
        require(value >= 0)
    }

    companion object {
        fun noThreshold(): SignificantActivityThreshold {
            return SignificantActivityThreshold(0.0)
        }
    }

}

