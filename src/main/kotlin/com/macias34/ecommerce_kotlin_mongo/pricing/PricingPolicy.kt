package com.macias34.ecommerce_kotlin_mongo.pricing

import com.macias34.ecommerce_kotlin_mongo.DateRange
import com.macias34.ecommerce_kotlin_mongo.Vendor
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.math.RoundingMode

class PricingPolicy(private val adjustment: Adjustment, private val applicability: Applicability) {
    fun apply(money: Money): Money {
        return adjustment.apply(money)
    }

    fun isApplicable(context: PricingContext): Boolean {
        return applicability.isApplicable(context);
    }

    companion object {
        fun ofPercentage(percentage: Double, applicability: Applicability): PricingPolicy {
            return PricingPolicy(Adjustment.ofPercentage(percentage), applicability)
        }

        fun ofValue(value: Double, applicability: Applicability): PricingPolicy {
            return PricingPolicy(Adjustment.ofValue(value), applicability)
        }
    }

}

data class Adjustment private constructor(val adjustmentValue: AdjustmentValue, val adjustmentType: AdjustmentType) {

    fun apply(money: Money): Money {
        if (adjustmentType == AdjustmentType.PERCENTAGE) {
            val remainder = AdjustmentValue.ofPercentage(100.00).plus(adjustmentValue).percentageValue()
            return money.multiply(remainder)
        }

        return money.add(Money.of(adjustmentValue.value, money.currency))
    }

    companion object {
        fun ofPercentage(percentage: Double): Adjustment {
            return Adjustment( AdjustmentValue.ofPercentage(percentage), AdjustmentType.PERCENTAGE)
        }

        fun ofValue(value: Double): Adjustment {
            return Adjustment(AdjustmentValue.ofValue(value), AdjustmentType.VALUE)
        }
    }
}

data class AdjustmentValue private constructor(val value: BigDecimal) {

    fun plus(subtract: AdjustmentValue): AdjustmentValue {
        return AdjustmentValue(value.plus(subtract.value))
    }

    fun percentageValue(): BigDecimal {
        return value.divide(BigDecimal(100))
    }

    companion object {
        fun ofValue(value: Double): AdjustmentValue {
            val scaledValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP)
            return AdjustmentValue(scaledValue)
        }

        fun ofPercentage(percentage: Double): AdjustmentValue {
            require(percentage in 0.0..100.0)

            val scaledPercentage = BigDecimal.valueOf(percentage).setScale(2, RoundingMode.HALF_UP)
            return AdjustmentValue(scaledPercentage)
        }
    }
}

data class Applicability(val vendor: Vendor, val dateRange: DateRange) {
    fun isApplicable(context: PricingContext): Boolean {
        val (vendor, date) = context
        return this.vendor == vendor && this.dateRange.contains(date)
    }
}


enum class AdjustmentType {
    PERCENTAGE,
    VALUE
}
