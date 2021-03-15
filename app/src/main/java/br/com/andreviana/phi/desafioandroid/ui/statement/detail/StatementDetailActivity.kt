package br.com.andreviana.phi.desafioandroid.ui.statement.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.andreviana.phi.desafioandroid.R
import br.com.andreviana.phi.desafioandroid.data.common.Constants.GENERIC_ERROR
import br.com.andreviana.phi.desafioandroid.data.common.Constants.STATEMENT_ID
import br.com.andreviana.phi.desafioandroid.data.common.DataState
import br.com.andreviana.phi.desafioandroid.databinding.ActivityStatementDetailBinding
import br.com.andreviana.phi.desafioandroid.util.helper.getBitmapFromView
import br.com.andreviana.phi.desafioandroid.util.helper.saveBitmapFile
import br.com.andreviana.phi.desafioandroid.util.ktx.showToastLong
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StatementDetailActivity : AppCompatActivity(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val viewModel: StatementDetailViewModel by viewModels()
    private val binding by lazy { ActivityStatementDetailBinding.inflate(layoutInflater) }

    private var mStatementId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadBundle()
        setupUi()
    }

    private fun loadBundle() {
        intent.extras?.getString(STATEMENT_ID)?.let { statementId ->
            mStatementId = statementId
            getStatementDetail()
        }
    }

    private fun setupUi() {
        binding.swipeRefreshProof.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.teal_custom_300
            )
        )
        binding.buttonShare.setOnClickListener(this)
        binding.imageViewBack.setOnClickListener(this)
        binding.swipeRefreshProof.setOnRefreshListener(this)
    }

    private fun getStatementDetail() {
        mStatementId?.let {
            viewModel.getStatementDetail(it).observe(this, { dataState ->
                when (dataState) {
                    is DataState.Loading -> showProgress()
                    is DataState.Success -> {
                        hideProgress()
                        val proof = viewModel.createViewProof(dataState.data)
                        binding.recyclerViewProof.adapter = StatementDetailAdapter(proof)
                    }
                    is DataState.Failure -> {
                        hideProgress()
                        showToastLong(dataState.message)
                    }
                    is DataState.Error -> {
                        hideProgress()
                        showToastLong(GENERIC_ERROR)
                        Timber.tag(TAG).e(dataState.throwable)
                    }
                }
            })
        }
    }

    private fun photoShare() {
        hideViewToConvertToImage()
        val bitmap = getBitmapFromView(binding.viewStatementDetail)
        val createFile = saveBitmapFile(this, "Comprovante", bitmap)
        showViewToConvertToImage()

        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, createFile.toUri())
            putExtra(Intent.EXTRA_TITLE, resources.getString(R.string.proof))
            type = "image/png"
        }
        startActivity(Intent.createChooser(shareIntent, "Enviar comprovante"))
    }

    private fun showProgress() {
        binding.swipeRefreshProof.isRefreshing = true
    }

    private fun hideProgress() {
        binding.swipeRefreshProof.isRefreshing = false
    }

    private fun hideViewToConvertToImage() {
        binding.imageViewBack.visibility = View.GONE
        binding.buttonShare.visibility = View.GONE
    }

    private fun showViewToConvertToImage() {
        binding.imageViewBack.visibility = View.VISIBLE
        binding.buttonShare.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        getStatementDetail()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonShare -> photoShare()
            R.id.imageViewBack -> onBackPressed()
        }
    }

    companion object {
        private const val TAG = "StatementDetailActivity"
    }
}