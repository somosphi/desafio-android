package com.henrique.desafio_android.utils

import java.math.BigDecimal
import io.reactivex.Observable
import java.text.DecimalFormat

fun BigDecimal.formatCurrency(): String {
    return Observable.just(this)
        .map{ getDecimalFormatWithCurrency().format(it) }
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