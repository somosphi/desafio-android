package com.henrique.desafio_android.service.utils

import android.util.Log
import java.math.BigDecimal
import io.reactivex.Observable
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_PIX_OUT = "PIXCASHOUT"
const val DEFAULT_PIX_IN = "PIXCASHIN"
const val DEFAULT_TO = "Recebedor"
const val DEFAULT_FROM = "Pagador"

fun BigDecimal.formatCurrency(): String {
    return Observable.just(this)
        .map { getDecimalFormatWithCurrency().format(it) }
        .onExceptionResumeNext(Observable.just(""))
        .blockingFirst()
}

private fun getDecimalFormatWithCurrency(): DecimalFormat {
    val df = DecimalFormat("R$ #,##0.00;-R$ #,##0.00")
    val dfs = df.decimalFormatSymbols
    dfs.decimalSeparator = ','
    dfs.groupingSeparator = '.'
    df.decimalFormatSymbols = dfs

    return df
}

fun String.isPixTransaction(): Boolean {
    return when (this) {
        DEFAULT_PIX_IN, DEFAULT_PIX_OUT -> true
        else -> false
    }
}

fun String.getDateFormatted(): String {
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
        val date = dateFormat.parse(this)
        date?.let {
            return getDateToStringFormatted(date, "dd/MM")
        }
    } catch (e: ParseException) {
        e.localizedMessage?.let {
            Log.d("", it)
        }
    }
    return ""
}

fun String.getDateTimeFormatted(): String {
    try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
        val date = dateFormat.parse(this)
        date?.let {
            return getDateToStringFormatted(date, "dd/MM/yyyy")
        }
    } catch (e: ParseException) {
        e.localizedMessage?.let {
            Log.d("", it)
        }
    }
    return ""
}

private fun getDateToStringFormatted(date: Date, to: String): String {
    val simpleDateFormat = SimpleDateFormat(to, Locale("pt", "BR"))
    return simpleDateFormat.format(date)
}

fun getPerson(to: String, from: String): String {
    return if (to != "") to else from
}

fun getPersonType(to: String): String {
    return if (to != "") DEFAULT_TO else DEFAULT_FROM
}