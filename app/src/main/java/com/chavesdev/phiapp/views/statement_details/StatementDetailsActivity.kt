package com.chavesdev.phiapp.views.statement_details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chavesdev.phiapp.PhiAppApplication
import com.chavesdev.phiapp.R
import com.chavesdev.phiapp.databinding.ActivityStatementDetailsBinding
import com.chavesdev.phiapp.di.modules.factory.ViewModelFactory
import com.chavesdev.phiapp.repo.api.messages.StatementMessage
import com.chavesdev.phiapp.util.format
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class StatementDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityStatementDetailsBinding
    private lateinit var statementDetailsViewModel: StatementDetailViewModel
    private lateinit var detailsViewModel: DetailViewModel
    private lateinit var screenshot: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statement_details)
        screenshot = binding.receiptContainer

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (this.application as PhiAppApplication).phiAppComponent.inject(this)

        statementDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(StatementDetailViewModel::class.java)
        detailsViewModel = DetailViewModel()
        binding.details = detailsViewModel
        binding.lifecycleOwner = this

        statementDetailsViewModel.details.observe(this, {
            detailsViewModel.parseMessage(it)
        })

        intent.getStringExtra(statementId)?.let {
            statementDetailsViewModel.getDetails(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }

    fun shareReceipt(view: View) {
        takeScreenshot()
    }

    //TODO separate this to a singleton
    private fun takeScreenshot() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0
                );
            }
        } else {
            try {
                val imageFile = File(
                    getOutputDirectory(this), SimpleDateFormat(
                        "yyyyMMddhhmmss",
                        Locale.getDefault()
                    )
                        .format(System.currentTimeMillis()) + ".jpg"
                )

                val v1 = window.decorView.rootView //temp view
//                val v1: View = screenshot //ideal view
                v1.isDrawingCacheEnabled = true
                v1.buildDrawingCache(true)
                val bitmap = Bitmap.createBitmap(v1.drawingCache)
                v1.isDrawingCacheEnabled = false
                v1.buildDrawingCache(false)
                val outputStream = FileOutputStream(imageFile)
                val quality = 100
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                outputStream.flush()
                outputStream.close()
                openScreenshot(imageFile)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun getOutputDirectory(context: Context): File {
        val appContext = context.applicationContext
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else appContext.filesDir
    }


    private fun openScreenshot(imageFile: File) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        val chooser = Intent.createChooser(shareIntent, getString(R.string.statement_details_title) )
        val imageUri = FileProvider.getUriForFile(
            this,
            "com.chavesdev.phiapp.fileprovider",
            imageFile
        )
        shareIntent.setDataAndType(imageUri, "image/*")
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

        val resInfoList: List<ResolveInfo> =
            this.packageManager.queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY)

        for (resolveInfo in resInfoList) {
            val packageName: String = resolveInfo.activityInfo.packageName
            grantUriPermission(
                packageName,
                imageUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        startActivity(chooser)
    }

    companion object {
        const val statementId = "statement.id"
    }

    class DetailViewModel(
        val type: MutableLiveData<String>,
        val amount: MutableLiveData<String>,
        val receiver: MutableLiveData<String>,
        val bankName: MutableLiveData<String>,
        val datetime: MutableLiveData<String>,
        val auth: MutableLiveData<String>
    ) : ViewModel() {
        constructor() : this(
            MutableLiveData(""),
            MutableLiveData(""),
            MutableLiveData(""),
            MutableLiveData(""),
            MutableLiveData(""),
            MutableLiveData("")
        )

        fun parseMessage(message: StatementMessage?) {
            message?.let {
                type.postValue(it.description)
                amount.postValue(it.formatedNumber())
                receiver.postValue(it.getOrigin())
                datetime.postValue(it.date.format("dd/MM - HH:mm:ss"))
                bankName.postValue(it.bankName)
                auth.postValue(it.authentication)
            }
        }
    }
}