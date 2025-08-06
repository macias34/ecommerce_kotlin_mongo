package com.macias34.ecommerce_kotlin_mongo.pricing

import com.macias34.ecommerce_kotlin_mongo.DateRange
import com.macias34.ecommerce_kotlin_mongo.Vendor
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant

class PricingPolicy(private val adjustment: Adjustment, private val applicability: Applicability) {
    fun apply(money: Money): Money {
        return adjustment.apply(money)
    }

    fun isApplicable(context: ApplicabilityContext): Boolean {
        return applicability.isApplicable(context);
    }

}

data class Adjustment private constructor(val adjustmentValue: AdjustmentValue, val adjustmentType: AdjustmentType) {

    fun apply(money: Money): Money {
        if (adjustmentType == AdjustmentType.PERCENTAGE) {
            val remainder = AdjustmentValue.ofPercentage(100.00).minus(adjustmentValue)
            return money.multiply(remainder.value)
        }

        return money.subtract(Money.of(adjustmentValue.value, money.currency))
    }

    companion object {
        fun of(value: Double, adjustmentType: AdjustmentType): Adjustment {
            val adjustmentValue = if (adjustmentType == AdjustmentType.PERCENTAGE) {
                AdjustmentValue.ofPercentage(value)
            } else {
                AdjustmentValue.ofValue(value)
            }

            return Adjustment(adjustmentValue, adjustmentType)
        }
    }
}

data class AdjustmentValue private constructor(val value: BigDecimal) {

    fun minus(subtract: AdjustmentValue): AdjustmentValue {
        return AdjustmentValue(value.subtract(subtract.value))
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
    fun isApplicable(context: ApplicabilityContext): Boolean {
        val (vendor, date) = context
        return this.vendor == vendor && this.dateRange.contains(date)
    }
}

data class ApplicabilityContext(val vendor: Vendor, val date: Instant)

enum class AdjustmentType {
    PERCENTAGE,
    VALUE
}
