package com.newton.phi.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun currencyFormat(value: Int): String {
    val cash = value.toString()
    val df = DecimalFormat("##,###.##")
    df.minimumFractionDigits = 2
    df.isDecimalSeparatorAlwaysShown = true

    return df.format(cash
            .replace(Regex("[^\\d]"), "")
            .toDouble() / 100)
}

fun currencyDate(value: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ROOT)
    val mDate = formatter.parse(value) // this never ends while debugging

    val time = Calendar.getInstance()
    time.time = mDate!!

    return "${time.get(Calendar.DAY_OF_MONTH)}/${time.get(Calendar.MONTH)+1}"
}

fun currencyDateCompleted(value: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ROOT)
    val mDate = formatter.parse(value) // this never ends while debugging

    val time = Calendar.getInstance()
    time.time = mDate!!

    return "${time.get(Calendar.DAY_OF_MONTH)}/${time.get(Calendar.MONTH)+1}/${time.get(Calendar.YEAR)} - " +
            "${time.get(Calendar.HOUR)}:${time.get(Calendar.MINUTE)}:${time.get(Calendar.SECOND)}"
}

fun isNetworkAvailable(context: Context?): Boolean {
    if (context == null) return false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}