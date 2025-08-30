package com.macias34.ecommerce_kotlin_mongo.salesTrendAnalysis

import com.macias34.ecommerce_kotlin_mongo.DateRange
import com.macias34.ecommerce_kotlin_mongo.Event
import java.time.Instant
import java.util.UUID

data class SignificantSalesActivityDetected(val productId: UUID, val dateRange: DateRange, val value: Double, val status: TrendFactorStatus,
                                            override val eventId: UUID = UUID.randomUUID(),
                                            override val occurredAt: Instant = Instant.now()
): Event