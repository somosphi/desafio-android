package com.example.phitest.util

import java.text.NumberFormat
import java.util.*

class VsFunctions {

    companion object {
        fun formatToCurrency(value: Double): String {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            return numberFormat.format(value)
        }

        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1

        const val TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    }
}