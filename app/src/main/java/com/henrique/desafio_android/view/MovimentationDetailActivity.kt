package com.henrique.desafio_android.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.henrique.desafio_android.BR
import com.henrique.desafio_android.R
import com.henrique.desafio_android.databinding.ActivityMovimentationDetailBinding
import com.henrique.desafio_android.service.repository.GetMovimentationInteractor
import com.henrique.desafio_android.viewmodel.MovimentationDetailViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MovimentationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovimentationDetailBinding

    private val movimentationInteractor : GetMovimentationInteractor by inject {
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

        observe()
    }

    private fun observe() {
        mViewModel.shouldCloseActivity.observe(this, {
            if (it) {
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getStatementDetail()
    }

    companion object {
        const val MOVIMENTATION_ID_EXTRAS = "movimentation_id_extras"
    }

}