package com.example.desafiophi.utils

import br.com.concrete.canarinho.formatador.FormatadorValor
import java.text.SimpleDateFormat
import java.util.*

private val brLocale = Locale("pt", "BR")

fun Double.maskBrazilianCurrency(withSymbol: Boolean = false): String = try {
    if (withSymbol)
        FormatadorValor.VALOR_COM_SIMBOLO.formata(this.toString())
    else
        FormatadorValor.VALOR.formata(this.toString())
} catch (e: IllegalArgumentException) {
    e.printStackTrace()
    ""
}

fun String.toBrazilianDateFormat(inputPattern: String, outputPattern: String): String {
    var dateString = this
    val inputDateFormat = SimpleDateFormat(inputPattern, brLocale)
    val outputDateFormat = SimpleDateFormat(outputPattern, brLocale)
    try {
        inputDateFormat.parse(this)?.let { dateFormatted ->
            dateString = outputDateFormat.format(dateFormatted)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return dateString
}