package com.macias34.ecommerce_kotlin_mongo.product

import com.macias34.ecommerce_kotlin_mongo.common.MoneyData
import com.macias34.ecommerce_kotlin_mongo.common.Vendor
import java.util.UUID

data class ProductData(
    val id: UUID,
    val name: String,
    val vendor: Vendor,
    val price: MoneyData,
    val stock: Long
)