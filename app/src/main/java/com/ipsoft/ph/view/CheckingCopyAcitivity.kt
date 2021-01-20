package com.ipsoft.ph.view

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.view.drawToBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ipsoft.ph.BuildConfig
import com.ipsoft.ph.R
import com.ipsoft.ph.databinding.ActivityCheckingCopyAcitivityBinding
import com.ipsoft.ph.repository.HttpRepository
import com.ipsoft.ph.repository.model.Transaction
import com.ipsoft.ph.viewmodel.MainViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class CheckingCopyAcitivity : AppCompatActivity() {
    private lateinit var checkingBinding: ActivityCheckingCopyAcitivityBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkingBinding = ActivityCheckingCopyAcitivityBinding.inflate(layoutInflater)
        val view = checkingBinding.root
        setContentView(view)


        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {

            title = ""
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)

        }

        val id = intent.getStringExtra("id") ?: ""

        initViewModel()


        viewModel.getDetailTransaction(id).observe(this, Observer {
            setValues(it)
        })

        checkingBinding.buttonShare.setOnClickListener {

            var checkView = checkingBinding.checkCopyArea
            takeScreenCapture(checkView)

        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setValues(transaction: Transaction) {

        if (transaction != null) {

            checkingBinding.textAuth.text = transaction.authentication
            checkingBinding.textViewTransactionType.text = transaction.tType
            checkingBinding.textReceiver.text = transaction.sender
            checkingBinding.textViewValue.text =
                "R$ ${transaction.amount.toString().replace(".", ",")}"
            checkingBinding.textdate.text = transaction.createdAd
        } else {
            Toast.makeText(this, getString(R.string.transaction_info_error), Toast.LENGTH_SHORT)
                .show()
            finish()
        }

    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(
            this, MainViewModel.MainViewModelFactory(
                HttpRepository
            )
        ).get(MainViewModel::class.java)

    }

    private fun takeScreenCapture(v: View) {

        val b: Bitmap = v.drawToBitmap(Bitmap.Config.ARGB_8888)

        val cw = ContextWrapper(applicationContext)

        val directory: File = cw.filesDir

        val imageFile = File(directory, UUID.randomUUID().toString() + ".jpg")

        Log.d("path", imageFile.toString())
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(imageFile)
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val contentUri: Uri =
            getUriForFile(
                applicationContext,
                BuildConfig.APPLICATION_ID + ".provider", imageFile
            )


        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/jpeg"
        shareIntent.putExtra(
            Intent.EXTRA_STREAM, contentUri
        )
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))


    }
}


