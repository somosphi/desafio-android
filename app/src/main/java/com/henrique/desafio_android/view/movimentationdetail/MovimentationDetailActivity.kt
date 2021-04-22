package com.henrique.desafio_android.view.movimentationdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.henrique.desafio_android.BR
import com.henrique.desafio_android.R
import com.henrique.desafio_android.databinding.ActivityMovimentationDetailBinding
import com.henrique.desafio_android.domain.repository.GetMovimentationInteractor
import com.henrique.desafio_android.domain.utils.shareReceipt
import com.henrique.desafio_android.viewmodel.movimentationdetail.MovimentationDetailViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class MovimentationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovimentationDetailBinding

    private val movimentationInteractor: GetMovimentationInteractor by inject {
        parametersOf()
    }

    private val mViewModel: MovimentationDetailViewModel by inject {
        parametersOf(movimentationInteractor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movimentation_detail)
        binding.lifecycleOwner = this@MovimentationDetailActivity
        binding.setVariable(BR.viewModel, mViewModel)

        intent.run {
            getStringExtra(MOVIMENTATION_ID_EXTRAS)?.let {
                mViewModel.movimentationId.value = it
            }
        }

        observeCloseActivity()
        observeShareActivity()
    }

    private fun observeCloseActivity() {
        mViewModel.shouldCloseActivity.observe(this, {
            if (it) {
                finish()
            }
        })
    }

    private fun observeShareActivity() {
        mViewModel.shouldShareActivity.observe(this, {
            if (it) {
                changeVisibilityComponents(View.GONE)
                shareReceipt(this, binding.root).let { intent ->
                    this.startActivityForResult(intent, SHARING_REQUEST_CODE)
                }
                changeVisibilityComponents(View.VISIBLE)
            }
        })
    }

    private fun changeVisibilityComponents(visibility: Int) {
        binding.movimentationDetailNavbar.visibility = visibility
        binding.movimentationDetailShareButton.visibility = visibility
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getStatementDetail()
    }

    companion object {
        const val MOVIMENTATION_ID_EXTRAS = "movimentation_id_extras"
        const val SHARING_REQUEST_CODE = 622
    }

}