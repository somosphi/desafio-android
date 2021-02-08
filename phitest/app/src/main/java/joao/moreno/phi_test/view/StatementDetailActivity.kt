package joao.moreno.phi_test.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import joao.moreno.phi_test.utils.view.DialogLoadingFullScreen
import joao.moreno.phi_test.R
import joao.moreno.phi_test.view.vm.StatementDetailViewModel
import joao.moreno.phi_test.view.vm.StatementDetailViewModelFactory
import joao.moreno.phi_test.databinding.ActivityStatementDetailBinding
import joao.moreno.phi_test.model.statement.ItemStatementVO
import joao.moreno.phi_test.model.statement.StatementType
import joao.moreno.phi_test.utils.ViewUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class StatementDetailActivity : AppCompatActivity() {

    lateinit var viewModel: StatementDetailViewModel
    lateinit var binding: ActivityStatementDetailBinding

    lateinit var itemStatemnetVO: ItemStatementVO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_statement_detail
        )
        itemStatemnetVO = intent.getSerializableExtra(MainActivity.KEY_INTENT) as ItemStatementVO
        viewModel =
            ViewModelProvider(this, StatementDetailViewModelFactory(itemStatemnetVO.id!!)).get(
                StatementDetailViewModel::class.java
            )

        val dialogLoading = DialogLoadingFullScreen(this)
        viewModel.statementDetailLiveData.observe(this, { result ->
            binding.model = result
            dialogLoading.dismiss()
            when (result.tType) {
                StatementType.TRANSFEROUT, StatementType.PIXCASHOUT, StatementType.BANKSLIPCASHOUT -> binding.labelOperationAmountValue.text =
                    ViewUtils.formatNegativeValueCurrency(result.amount)
                StatementType.TRANSFERIN, StatementType.PIXCASHIN, StatementType.BANKSLIPCASHIN -> binding.labelOperationAmountValue.text =
                    ViewUtils.formatValueCurrency(result.amount)
            }
        })

        viewModel.loading.observe(this, { isLoading ->
            if(isLoading) {
                dialogLoading.show()
            }
        })

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnShare.setOnClickListener {

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(
                    Intent.EXTRA_STREAM,
                    getImageUri(
                        this@StatementDetailActivity,
                        getBitmapFromView(binding.llShareReceipt)!!
                    )
                )
            }
            startActivity(Intent.createChooser(intent, "Escolha a plataforma de compartilhamento"))
        }
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, null, null)
        return if (path != null) {
            Uri.parse(path)
        } else {
            Uri.fromFile(
                saveBitmapExternalCacheDir(
                    inContext,
                    inImage,
                    "Comprovante",
                    CompressFormat.JPEG,
                    100
                )
            )
        }
    }

    private fun saveBitmapExternalCacheDir(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
        compressFormat: CompressFormat,
        quality: Int
    ): File {
        val file = File(context.externalCacheDir, fileName)
        try {
            if (file.exists() || file.createNewFile()) {
                val fileOutputStream = FileOutputStream(file)
                val bos = ByteArrayOutputStream()
                bitmap.compress(compressFormat, quality, bos)
                fileOutputStream.write(bos.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

}