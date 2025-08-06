package com.macias34.ecommerce_kotlin_mongo.pricing

import com.macias34.ecommerce_kotlin_mongo.Vendor
import org.javamoney.moneta.Money
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product_pricing")
class ProductPricing(@Id val id: UUID, private val basePrice: Money, private val policies: List<PricingPolicy>) {
    fun priceFor(vendor: Vendor): Money {
        return basePrice;
    }
}