package com.macias34.ecommerce_kotlin_mongo

import org.javamoney.moneta.Money
import java.math.BigDecimal

data class MoneyData(
    val value: BigDecimal,
    val currency: String
)

fun Money.toData(): MoneyData {
    return MoneyData(
        value = this.number.numberValue(BigDecimal::class.java),
        currency = this.currency.currencyCode
    )
}