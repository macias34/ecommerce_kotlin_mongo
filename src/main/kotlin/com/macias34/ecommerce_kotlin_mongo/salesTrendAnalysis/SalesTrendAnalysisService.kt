package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

class SalesTrendAnalysisService {
    fun detectSignificantSalesActivity(baseSalesRate: SalesRate, currentSalesRate: SalesRate, trendFactorStatusConfiguration: TrendFactorStatusConfiguration): SignificantSalesActivity? {
        val trendFactor = TrendFactor.calculate(baseSalesRate, currentSalesRate, trendFactorStatusConfiguration)

        return SignificantSalesActivity(trendFactor.value, trendFactor.status)
    }
}