package com.chavesdev.phiapp.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Long.formatNumber() : String {
    val ptBr = Locale("pt", "BR")
    val formater: DecimalFormat = NumberFormat.getCurrencyInstance(ptBr) as DecimalFormat
    val symbol: String = Currency.getInstance(ptBr).getSymbol(ptBr)
    formater.isGroupingUsed = true
    formater.positivePrefix = "$symbol "
    formater.negativePrefix = "-$symbol "
    return formater.format(this)
}

fun Date.format(format: String) : String {
    return android.text.format.DateFormat.format(format, this).toString()
}