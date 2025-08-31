package com.macias34.ecommerce_kotlin_mongo.common

interface EventPublisher {
    fun publish(event: Event)
    fun publish(events: List<Event>)
}