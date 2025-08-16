package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

class SalesTrendAnalysisService {
    fun detectSignificantSalesActivity(
        baseSalesRate: SalesRate, currentSalesRate: SalesRate,
        trendFactorStatusConfiguration: TrendFactorStatusConfiguration,
        significantActivityThreshold: SignificantActivityThreshold = SignificantActivityThreshold.noThreshold()
    ): SignificantSalesActivity? {
        val trendFactor = TrendFactor.calculate(baseSalesRate, currentSalesRate, trendFactorStatusConfiguration)

        if(!significantActivityThreshold.isActivitySignificant(baseSalesRate, currentSalesRate)){
            return null
        }

        return SignificantSalesActivity(trendFactor.value, trendFactor.status)
    }
}