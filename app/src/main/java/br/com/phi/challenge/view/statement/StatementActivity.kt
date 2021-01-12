package br.com.phi.challenge.view.statement

import br.com.phi.challenge.R
import br.com.phi.challenge.databinding.ActivityStatementBinding
import br.com.phi.challenge.view.base.BaseActivity
import br.com.phi.challenge.view.statementdetail.StatementDetailActivity
import br.com.phi.challenge.viewmodel.base.toolbar.Toolbar
import br.com.phi.challenge.viewmodel.statement.StatementViewModel

class StatementActivity : BaseActivity<ActivityStatementBinding, StatementViewModel>(StatementViewModel::class, R.layout.activity_statement) {

    override fun initActivity() {
        getInformation()
        viewModel.statementId.observe({lifecycle}) { id -> id?.let { openStatementDetail(it) } }
    }

    override fun setupToolbar() = with(viewBinding) {
        toolbarVM = toolbarViewModel
        toolbarViewModel.setToolbar(Toolbar(R.string.title_statement))
        setupToolbarTitle(statementToolbar.contentToolbar)
    }

    private fun getInformation() {
        viewModel.getBalance()
        viewModel.getStatements()
    }

    private fun openStatementDetail(statementId: String) =startActivity(StatementDetailActivity.newIntent(this, statementId))
}