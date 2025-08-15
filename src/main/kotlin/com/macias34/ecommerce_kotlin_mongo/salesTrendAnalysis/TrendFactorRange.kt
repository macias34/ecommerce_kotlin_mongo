package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

data class TrendFactorRange(val start: TrendFactorValue, val end: TrendFactorValue?) {
    fun contains(value: TrendFactorValue): Boolean {
        return value.value >= start.value && value.value < (end?.value ?: Double.POSITIVE_INFINITY)
    }

    companion object {
        fun fromToInfinite(start: Double): TrendFactorRange {
            return TrendFactorRange(TrendFactorValue(start), null)
        }

        fun of(start: Double, end: Double): TrendFactorRange {
            return TrendFactorRange(TrendFactorValue(start), TrendFactorValue(end))
        }
    }
}
