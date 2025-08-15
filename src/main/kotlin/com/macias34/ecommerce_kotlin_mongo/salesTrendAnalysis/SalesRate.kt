package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import java.time.Duration


data class SalesRate private constructor(val value: SalesRateValue, val duration: Duration)
{
    companion object {
        fun of(value: Long, duration: Duration): SalesRate {
            return SalesRate(SalesRateValue(value), duration)
        }
    }
}


data class SalesRateValue(val value: Long){
    init {
        require(value >= 0)
    }
}
