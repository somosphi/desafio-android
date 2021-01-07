package me.luanhenriquer8.phitest.ui.details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import me.luanhenriquer8.phitest.R
import me.luanhenriquer8.phitest.databinding.ActivityTransactionDetailBinding
import me.luanhenriquer8.phitest.ui.home.MainViewModel
import me.luanhenriquer8.phitest.utils.STATEMENT_ID
import java.lang.Exception


class TransactionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionDetailBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction_detail)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.lifecycleOwner = this
        initializeListeners()
        initializeObservers()
    }

    override fun onResume() {
        super.onResume()

        val extras = intent.extras
        val statementId = extras?.getString(STATEMENT_ID)
        if (statementId != null) {
            viewModel.fetchStatementDetail(statementId)
        }
    }

    private fun initializeObservers() {
        viewModel.myStatement.observe(this, {
            if (it != null) {
                binding.transactionType.text = it.tType
                binding.amount.text = it.extractAmountAsCurrency()
                binding.authentication.text = it.authentication
                binding.time.text = it.extractCreatedAtFormatted()

                binding.to.text = if (it.to != null) it.to else getString(R.string.unknown)
                binding.bankingInstitution.text =
                    if (it.bankName != null) it.bankName else getString(
                        R.string.unknown
                    )
            }
        })
    }

    private fun initializeListeners() {
        binding.backButton.setOnClickListener { onBackPressed() }
        binding.shareButton.setOnClickListener { shareScreenshot() }
    }

    private fun shareScreenshot() {
        try {
            mustShowStatementComponents(false)

            val decorView: View = binding.statementBlock.rootView
            val bitmap =
                Bitmap.createBitmap(decorView.width, decorView.height, Bitmap.Config.ARGB_8888)
            decorView.draw(Canvas(bitmap))
            val bitmapPath = Images.Media.insertImage(contentResolver, bitmap, "screenshot", null)

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, Uri.parse(bitmapPath))
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

            mustShowStatementComponents(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast
                .makeText(this, getString(R.string.operation_failed_try_again), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun mustShowStatementComponents(mustShow: Boolean) {
        binding.backButton.visibility = if (mustShow) View.VISIBLE else View.GONE
        binding.shareButton.visibility = if (mustShow) View.VISIBLE else View.GONE
    }
}