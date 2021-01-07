package com.example.desafiophi.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.core.content.FileProvider
import br.com.concrete.canarinho.formatador.FormatadorValor
import java.io.File
import java.io.FileOutputStream
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

fun View.getBitmap(): Bitmap {
    val viewBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.RGB_565)
    val viewCanvas = Canvas(viewBitmap)
    val backgroundDrawable = this.background
    if (backgroundDrawable != null) {
        backgroundDrawable.draw(viewCanvas)
    } else {
        viewCanvas.drawColor(Color.WHITE)
        this.draw(viewCanvas)
    }
    return viewBitmap
}

fun Bitmap.saveToCache(context: Context): Uri? {
    val imageUri: Uri?
    val file: File?
    val folder = File(
        context.cacheDir
            .toString() + File.separator
    )
    if (!folder.exists()) {
        folder.mkdir()
    }
    val filename = "img.jpg"
    file = File(folder.path, filename)

    imageUri = FileProvider.getUriForFile(
        context,
        context.packageName.toString() + ".my.package.name.provider",
        file
    )
    return imageUri
}
