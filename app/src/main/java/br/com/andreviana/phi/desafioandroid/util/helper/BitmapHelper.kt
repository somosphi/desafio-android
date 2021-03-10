package br.com.andreviana.phi.desafioandroid.util.helper

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.view.View
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

fun getBitmapFromView(view: View): Bitmap {
    val bitmapCreated = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmapCreated)

    val mode = view.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
    Timber.tag("getBitmapFromView").i("DEFAULT_NIGHT: $mode")
    if (mode == Configuration.UI_MODE_NIGHT_NO) canvas.drawColor(Color.WHITE)
    else canvas.drawColor(Color.BLACK)

    view.draw(canvas)
    return bitmapCreated
}

fun saveBitmapFile(context: Context, fileName: String, bitmap: Bitmap): String {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path
    val pictureFile = File("$storageDir/$fileName.png")

    try {
        pictureFile.createNewFile()
        val outPutStream = FileOutputStream(pictureFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outPutStream)
        outPutStream.flush()
        outPutStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return pictureFile.absolutePath
}