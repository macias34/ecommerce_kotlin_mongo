package com.macias34.ecommerce_kotlin_mongo

import java.time.Instant
import java.util.UUID

interface Event {
    val eventId: UUID
    val occurredAt: Instant
}