package com.example.meta.extensions

import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formatCurrency(amount: Int, type: String = ""): String {
    val COUNTRY = "BR"
    val LANGUAGE = "pt"

    return getSignal(type) + NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(amount)
}

private fun getSignal(type: String): String {
    val OUT = "OUT"

    if (type.contains(OUT)) {
        return "- "
    }

    return ""
}

fun formatDate(createdAt: String): String {
    val FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val DATE_MONTH_FORMAT = "dd/MM"

    val formatDateString = SimpleDateFormat(FULL_DATE_FORMAT)
    val formatDate = SimpleDateFormat(DATE_MONTH_FORMAT)

    try {
        val date = formatDateString.parse(createdAt)

        return formatDate.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}

fun formatDateAndHour(createdAt: String): String {
    val FULL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val DATE_MONTH_FORMAT = "dd/MM - HH:mm:ss"

    val formatDateString = SimpleDateFormat(FULL_DATE_FORMAT)
    val formatDate = SimpleDateFormat(DATE_MONTH_FORMAT)

    try {
        val date = formatDateString.parse(createdAt)

        return formatDate.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return ""
}