package br.com.phi.challenge.view.statementdetail

import android.content.Context
import android.content.Intent
import br.com.phi.challenge.R
import br.com.phi.challenge.databinding.ActivityStatementDetailBinding
import br.com.phi.challenge.utils.shareYourReceipt
import br.com.phi.challenge.view.base.BaseActivity
import br.com.phi.challenge.viewmodel.base.toolbar.Toolbar
import br.com.phi.challenge.viewmodel.statementdetail.StatementDetailViewModel

class StatementDetailActivity  : BaseActivity<ActivityStatementDetailBinding, StatementDetailViewModel>(StatementDetailViewModel::class, R.layout.activity_statement_detail) {

    override fun initActivity() {
        intent.extras?.getString(STATEMENT_ID)?.let { statementId -> viewModel.getStatementDetail(statementId) }
        viewBinding.btnShareReceipt.setOnClickListener { shareYourReceipt(viewBinding.info) }
    }

    override fun setupToolbar()  = with(viewBinding) {
        toolbarVM = toolbarViewModel
        toolbarViewModel.setToolbar(Toolbar(R.string.string_empty))
        setupToolbarTitle(statementDetailToolbar.contentToolbar, true)
        statementDetailToolbar.contentToolbar.setNavigationOnClickListener { finish() }
    }

    companion object {

        private const val STATEMENT_ID = "STATEMENT_ID"

        fun newIntent(context: Context, statementId: String): Intent {
            return Intent(context, StatementDetailActivity::class.java).apply {
                putExtra(STATEMENT_ID, statementId)
            }
        }
    }
}