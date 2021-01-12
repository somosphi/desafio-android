package br.com.phi.challenge.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import br.com.phi.challenge.R
import java.io.File
import java.io.FileOutputStream
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by pcamilo on 10/01/2021.
 */

const val DATE_INPUT = "yyyy-MM-dd'T'HH:mm:ss"
const val DATE_STATEMENT = "dd/MM"
const val DATE_STATEMENT_DETAIL = "dd/MM/yyyy - HH:mm:ss"
const val IMAGE_QUALITY = 100

fun BigDecimal.formatToCurrencyBRL(): String = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this).toString()

@SuppressLint("SimpleDateFormat")
fun String.formatDateBR(output: String): String? {
    return try {
        val parser = SimpleDateFormat(DATE_INPUT)
        val formatter = SimpleDateFormat(output)
        parser.parse(this)?.let {
            formatter.format(it)
        }
    } catch (e: Exception){
        ""
    }
}

fun String.statementIsPix() = this.startsWith(PIX, ignoreCase = true)

fun Activity.shareYourReceipt(view: View) {
    val imageToShare = view.bitmapFromImage()

    if (imageToShare != null) {
        try {
            val fileToShare = File(this.externalCacheDir, getString(R.string.file_name_shared))
            val fileOutput = FileOutputStream(fileToShare)
            imageToShare.compress(Bitmap.CompressFormat.PNG, IMAGE_QUALITY, fileOutput)
            fileOutput.flush()
            fileOutput.close()
            setupShareIntent(FileProvider.getUriForFile(applicationContext, "${packageName}.fileprovider", fileToShare))
        } catch (e: Exception) {
            Toast.makeText(this, this.getString(R.string.error_to_share_receipt), Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(this, this.getString(R.string.error_to_share_receipt), Toast.LENGTH_SHORT).show()
    }
}

fun View.bitmapFromImage(): Bitmap? {
    val viewBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
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

fun Activity.setupShareIntent(imageUri: Uri): Intent {
    return Intent().apply {
        action = Intent.ACTION_SEND
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        putExtra(Intent.EXTRA_STREAM, imageUri)
        type = "image/png"
    }.also {
        startActivity(Intent.createChooser(it, this.baseContext.getString(R.string.app_name)))
    }
}