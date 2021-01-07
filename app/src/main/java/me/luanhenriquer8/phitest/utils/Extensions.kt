package me.luanhenriquer8.phitest.utils

import java.text.NumberFormat
import java.util.*

fun extractValueAsCurrency(amount: Long): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance(BRAZIL_CURRENCY)
    return format.format(amount)
}