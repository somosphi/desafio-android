package com.henrique.desafio_android.domain.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import com.henrique.desafio_android.R
import io.reactivex.Observable
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_PIX_OUT = "PIXCASHOUT"
const val DEFAULT_PIX_IN = "PIXCASHIN"
const val DEFAULT_TO = "Recebedor"
const val DEFAULT_FROM = "Pagador"
const val DEFAULT_DATE_FILE_FORMAT = "ddMMyyyyhhmmssSSS"
const val DEFAULT_FILE_FORMAT = ".jpg"

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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", getLocale())
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
            return getDateToStringFormatted(date, "dd/MM/yyyy") + " - " + this.substring(11..18)
        }
    } catch (e: ParseException) {
        e.localizedMessage?.let {
            Log.d("", it)
        }
    }
    return ""
}

private fun getDateToStringFormatted(date: Date, to: String): String {
    val simpleDateFormat = SimpleDateFormat(to, getLocale())
    return simpleDateFormat.format(date)
}

fun getPerson(to: String, from: String): String {
    return if (to != "") to else from
}

fun getPersonType(to: String): String {
    return if (to != "") DEFAULT_TO else DEFAULT_FROM
}

fun shareReceipt(context: Context, screenView: View): Intent {
    val bitmap = takeScreenshot(screenView)
    val file = saveBitmap(context, bitmap)

    val uri = getUriFromFile(context, file)

    val sharingIntent = Intent(Intent.ACTION_SEND).apply {
        this.type = "image/*"
        this.putExtra(Intent.EXTRA_STREAM, uri)
        this.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    return Intent.createChooser(
        sharingIntent,
        context.getString(R.string.movimentation_receipt_title)
    )
}

private fun takeScreenshot(screenView: View): Bitmap {
    val bitmap =
        Bitmap.createBitmap(screenView.width, screenView.height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(bitmap)
    screenView.draw(canvas)

    return bitmap
}

private fun saveBitmap(context: Context, bitmap: Bitmap): File {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
    val dateFormat = SimpleDateFormat(DEFAULT_DATE_FILE_FORMAT, getLocale())
    val fileName = dateFormat.format(Date()) + DEFAULT_FILE_FORMAT

    return createFile(context, bytes.toByteArray(), fileName)
}

private fun createFile(context: Context, bytes: ByteArray, fileName: String): File {
    val file =
        File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + File.separator + fileName)
    FileOutputStream(file).use { out -> out.write(bytes) }

    return file
}

private fun getUriFromFile(context: Context, file: File): Uri {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )
    else
        Uri.fromFile(file)
}

private fun getLocale(): Locale {
    return Locale("pt", "BR")
}