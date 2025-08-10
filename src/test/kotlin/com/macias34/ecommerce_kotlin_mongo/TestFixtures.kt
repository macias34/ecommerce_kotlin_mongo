package com.macias34.ecommerce_kotlin_mongo

import org.javamoney.moneta.Money

class TestFixtures {
    companion object {
        fun money(value: Number): Money {
            return Money.of(value, "PLN")
        }
    }

}