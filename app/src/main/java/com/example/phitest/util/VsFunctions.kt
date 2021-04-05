package com.example.phitest.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException
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

        const val TRANSACTION_API_EXCEPTION_MSG = "Falha na comunicação com a API"
        const val TRANSACTION_SHARE_EXCEPTION_MSG = "Falha no compartilhamento do comprovante"
        private const val TRANSACTION_INTERNET_MSG = "Verifique sua conexão com a Internet"


        @SuppressLint("NewApi")
        fun toDateDayMonthString(date: String) : String {
            try {
                val date = ZonedDateTime.parse(date)
                return "${date.dayOfMonth}/${date.monthValue}"
            } catch (e: DateTimeParseException) {
                e.message?.let { Log.e("VsFunctions", it) }
            }
            return ""
        }


        @SuppressLint("NewApi")
        fun toDateTimeString(date: String) : String {
            try {
                val date = ZonedDateTime.parse(date)
                return "${date.dayOfMonth}/${date.monthValue}/${date.year} ${String.format("%02d", date.hour)}:${String.format("%02d", date.minute)}:${String.format("%02d", date.second)}"
            } catch (e: DateTimeParseException) {
                e.message?.let { Log.e("VsFunctions", it) }
            }
            return ""
        }

        @SuppressLint("NewApi")
        fun isOnline(context: Context): String {

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                return TRANSACTION_API_EXCEPTION_MSG
            }

            return TRANSACTION_INTERNET_MSG
        }
    }
}