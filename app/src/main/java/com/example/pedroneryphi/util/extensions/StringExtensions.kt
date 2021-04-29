package com.example.pedroneryphi.util.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun String.formatBalancePresentation() : String{
    return "R$$this,00"
}

fun String.formateDate() : String {
    var newDate = ""
    val sd1 = SimpleDateFormat("yyyy-MM-dd")
    try {
        sd1.timeZone = TimeZone.getTimeZone("GMT")
        val dt = sd1.parse(this)
        val sd2 = SimpleDateFormat("dd/MM")
        newDate = sd2.format(dt)
    } catch (ex: Exception) {

    }
    return newDate
}

fun String.formateDateHour() : String {
    var newDate = ""
    val sd1 = SimpleDateFormat("yyyy-MM-dd")
    try {
        sd1.timeZone = TimeZone.getTimeZone("GMT")
        val dt = sd1.parse(this)
        val sd2 = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        newDate = sd2.format(dt)
    } catch (ex: Exception) {

    }
    return newDate
}
