package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.util.UUID


class SalesRateSpec : FunSpec({
    val iphoneId = UUID.randomUUID()
    val clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
    val date = Instant.now(clock)
    val salesRateCalculator = SalesRateCalculator(clock)

    fun minutesAgo(value: Long): Instant {
        return date.minus(Duration.ofMinutes(value))
    }

    test("Calculating sales rate with recent sales") {
        // Given
        val sales = listOf(
            Sale.of(iphoneId, 5, minutesAgo(10)),
            Sale.of(iphoneId, 3, minutesAgo(45)),
            Sale.of(iphoneId, 2, minutesAgo(70)),
        )

        // When
        val salesRate = salesRateCalculator.calculate(sales, Duration.ofHours(1))

        // Then
        salesRate shouldBe SalesRate.of(8, Duration.ofHours(1))
    }

    test("Calculating sales rate with a sale on the boundary") {
        // Given
        val sales = listOf(
            Sale.of(iphoneId, 6, minutesAgo(30)),
            Sale.of(iphoneId, 4, minutesAgo(60))
        )

        // When
        val salesRate = salesRateCalculator.calculate(sales, Duration.ofHours(1))

        // Then
        salesRate shouldBe SalesRate.of(10, Duration.ofHours(1))
    }

    test("Calculating sales rate with no recent sales") {
        // Given
        val sales = listOf(
            Sale.of(iphoneId, 10, minutesAgo(90)),
            Sale.of(iphoneId, 5, minutesAgo(120))
        )

        // When
        val salesRate = salesRateCalculator.calculate(sales, Duration.ofHours(1))

        // Then
        salesRate shouldBe SalesRate.of(0, Duration.ofHours(1))
    }

})

