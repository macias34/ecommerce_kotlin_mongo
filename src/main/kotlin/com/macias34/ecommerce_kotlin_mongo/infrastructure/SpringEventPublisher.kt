package com.macias34.ecommerce_kotlin_mongo.infrastructure

import com.macias34.ecommerce_kotlin_mongo.common.Event
import com.macias34.ecommerce_kotlin_mongo.common.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisher(val eventPublisher: ApplicationEventPublisher) : EventPublisher {
    override fun publish(event: Event) {
        eventPublisher.publishEvent(event)
    }

    override fun publish(events: List<Event>) {
        for (event in events) publish(event)
    }
}