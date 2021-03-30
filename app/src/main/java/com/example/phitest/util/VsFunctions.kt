package com.example.phitest.util

import java.text.NumberFormat
import java.util.*

class VsFunctions {

    companion object {
        fun formatToCurrency(value: Double): String {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            return numberFormat.format(value)
        }
    }
}