package br.com.andreviana.phi.desafioandroid.util.ktx

import java.text.NumberFormat
import java.util.*

fun Double.moneyFormat(): String =
        NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)

fun convertCentsToReal(amount: Int): Double = amount / 100.0
