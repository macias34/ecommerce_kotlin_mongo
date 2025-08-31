package com.macias34.ecommerce_kotlin_mongo.common

import java.time.Instant

data class DateRange(val start: Instant, val end: Instant) {
    init {
        require(!end.isBefore(start)) {
            "DateRange end ($end) must not be before start ($start)"
        }
    }

    operator fun contains(date: Instant): Boolean {
        return !date.isBefore(start) && !date.isAfter(end)
    }
}