package com.macias34.ecommerce_kotlin_mongo.pricing

import com.macias34.ecommerce_kotlin_mongo.common.Vendor
import java.time.Instant

data class PricingContext(val vendor: Vendor, val date: Instant)
