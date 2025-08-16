package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

data class SignificantSalesActivity(val value: TrendFactorValue, val status: TrendFactorStatus){
    companion object {
        fun of(value: Double, status: TrendFactorStatus): SignificantSalesActivity {
            return SignificantSalesActivity(TrendFactorValue(value), status)
        }
    }
}