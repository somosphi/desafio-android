package br.com.andreviana.phi.desafioandroid.util.helper

import java.text.SimpleDateFormat
import java.util.*


object DateFormat {
    @JvmStatic
    fun dateSimpleFormat(date: Date): String =
        SimpleDateFormat("dd/MM", Locale("pt", "BR")).format(date)

    fun dateHourCompleteFormat(date: Date): String =
        SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale("pt", "BR")).format(date)
}
