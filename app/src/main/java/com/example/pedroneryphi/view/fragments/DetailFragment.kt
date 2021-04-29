package com.example.pedroneryphi.view.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Handler
import android.os.StrictMode
import android.view.View
import androidx.lifecycle.Observer
import com.example.pedroneryphi.R
import com.example.pedroneryphi.base.BaseFragment
import com.example.pedroneryphi.databinding.TransferDetailFragmentBinding
import com.example.pedroneryphi.util.extensions.formatBalancePresentation
import com.example.pedroneryphi.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream


class DetailFragment : BaseFragment<TransferDetailFragmentBinding>() {

    private var id : String? = null

    override val fragmentLayout: Int = R.layout.transfer_detail_fragment

    private val detailViewModel: DetailViewModel by viewModel()

    override fun init() {
        id = arguments?.getString("transferId")
        callTransferDetail()
        initButtonShare()
        initObservables()
    }

    private fun initObservables() {
        detailViewModel.transferDetail.observe(this, Observer {
            bind.tvDescription.text = it.description
            bind.tvValue.text = it.value
            bind.tvName.text = it.name
            bind.tvDate.text = it.data
            bind.tvAuthentication.text = it.authentication
        })
    }

    override fun onResume() {
        super.onResume()
        bind.btShare.visibility = View.VISIBLE
    }

    private fun callTransferDetail(){
        id?.let { detailViewModel.findTransferDetail(it) }
    }

    private fun initButtonShare(){
        bind.btShare.setOnClickListener {
            bind.btShare.visibility = View.GONE
            val bitmap = loadBitmapFromView(bind.fullDetailLayout)
            try {
                bitmap?.let { sendBitmap(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendBitmap(bitmap: Bitmap){
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val externalCacheDier = context?.externalCacheDir
        val appName = resources.getString(R.string.app_name)
        val f = File("$externalCacheDier/$appName.png")
        val fileOutputStream = FileOutputStream(f)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        val shareInt = Intent(Intent.ACTION_SEND)
        shareInt.type = "image/*"
        shareInt.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f))
        shareInt.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(shareInt, "Share image"))
    }

    fun loadBitmapFromView(v: View): Bitmap? {
        val b = Bitmap.createBitmap(
            v.width,
            v.height,
            Bitmap.Config.ARGB_8888
        )
        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }
}