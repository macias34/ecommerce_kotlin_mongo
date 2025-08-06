package com.macias34.ecommerce_kotlin_mongo.infrastructure

import org.bson.Document
import org.javamoney.moneta.Money
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.math.BigDecimal
import javax.money.Monetary

@WritingConverter
class MoneyWriteConverter : Converter<Money, Document> {
    override fun convert(source: Money): Document {
        val doc = Document()
        doc["amount"] = source.number.numberValue(BigDecimal::class.java)
        doc["currency"] = source.currency.currencyCode
        return doc
    }
}

@ReadingConverter
class MoneyReadConverter : Converter<Document, Money> {
    override fun convert(source: Document): Money {
        val amount = source.get("amount", BigDecimal::class.java)
        val currency = Monetary.getCurrency(source.getString("currency"))
        return Money.of(amount, currency)
    }
}