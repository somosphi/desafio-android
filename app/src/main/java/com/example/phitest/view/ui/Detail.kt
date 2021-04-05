package com.example.phitest.view.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.phitest.R
import com.example.phitest.model.Transaction
import com.example.phitest.model.TransactionDetail
import com.example.phitest.view.viewmodel.Result
import com.example.phitest.util.VsFunctions
import com.example.phitest.view.viewmodel.TransactionDetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Detail : AppCompatActivity() {

    private lateinit var transaction: Transaction.Items
    private val viewModelTransaction: TransactionDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        transaction = intent.getSerializableExtra("transaction") as Transaction.Items

        val pbLoadingDetailData = findViewById<ProgressBar>(R.id.pbLoadingDetailData)
        pbLoadingDetailData.visibility = View.VISIBLE

        val linearLayout = findViewById<LinearLayout>(R.id.llContentShare)

        val captureButton = findViewById<Button>(R.id.btnShare)
        captureButton.setOnClickListener {

            // create BitMap from Layout
            val bitmap = createScreenShotFromDetail(linearLayout)

            if (bitmap != null) {
                //share the layout as an image
                shareTransactionDetail(bitmap)
            }
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        getDetailById(transaction.id)
    }

    private fun getDetailById(id: String){
        viewModelTransaction.getDetailById(id).observe(this){
            val transactionDetailVisible = it?.let { result ->
                when(result){
                    is Result.Success -> {
                        result.data?.let { transactionDetail ->
                            fullfillTransactionDetail(transactionDetail)
                            true
                        } ?: false
                    }
                    is Result.Error -> {
                        Toast.makeText(this, VsFunctions.isOnline(this), Toast.LENGTH_SHORT).show()
                        false
                    }
                }
            } ?: false

            val pbLoadingDetailData = findViewById<ProgressBar>(R.id.pbLoadingDetailData)
            runOnUiThread(java.lang.Runnable {
                pbLoadingDetailData.visibility = View.GONE
            })
        }
    }


    private fun fullfillTransactionDetail(transactionDetail: TransactionDetail){

        val txtTransactionTypeValueDetail = findViewById<TextView>(R.id.txtTransactionTypeValueDetail)
        val txtAmountValueDetail = findViewById<TextView>(R.id.txtAmountValueDetail)
        val txtToFromValueDetail = findViewById<TextView>(R.id.txtToFromValueDetail)
        val txtBankValueDetail = findViewById<TextView>(R.id.txtBankValueDetail)
        val txtDateTimeValueDetail = findViewById<TextView>(R.id.txtDateTimeValueDetail)
        val txtAuthenticationValueDetail = findViewById<TextView>(R.id.txtAuthenticationValueDetail)

        val llBankBlock = findViewById<LinearLayout>(R.id.llBankBlock)
        llBankBlock.visibility = View.GONE

        txtTransactionTypeValueDetail.text = transactionDetail.description
        txtAmountValueDetail.text = VsFunctions.formatToCurrency(transactionDetail.amount.toDouble())

        txtToFromValueDetail.text = transactionDetail.to

        if(transactionDetail.to.isNullOrEmpty()){
            txtToFromValueDetail.text = transactionDetail.from
        }

        if(!transactionDetail.bankName.isNullOrEmpty()) {
            txtBankValueDetail.text = transactionDetail.bankName
            llBankBlock.visibility = View.VISIBLE
        }

        txtDateTimeValueDetail.text = VsFunctions.toDateTimeString(transactionDetail.createdAt)
        txtAuthenticationValueDetail.text = transactionDetail.authentication
    }

    private fun createScreenShotFromDetail(v: View): Bitmap? {
        var screenshot: Bitmap? = null

        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            canvas.drawColor(Color.WHITE);
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("Detail.kt", "Failed to capture screenshot because:" + e.message)
            Toast.makeText(this, VsFunctions.TRANSACTION_SHARE_EXCEPTION_MSG, Toast.LENGTH_SHORT).show()
        }
        return screenshot
    }

    private fun shareTransactionDetail(bitmap: Bitmap) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(bitmap))
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun getBitmapFromView(bmp: Bitmap?): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(this.externalCacheDir, "shareDetailTransaction.jpg")

            val out = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)

        } catch (e: IOException) {
            Log.e("Detail.kt", "Failed to capture screenshot because:" + e.message)
            Toast.makeText(this, VsFunctions.TRANSACTION_SHARE_EXCEPTION_MSG, Toast.LENGTH_SHORT).show()
        }
        return bmpUri
    }

}