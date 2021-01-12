package br.com.phi.challenge.viewmodel.statement

import android.app.Application
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.phi.challenge.R
import br.com.phi.challenge.api.Result
import br.com.phi.challenge.api.balance.data.local.BalancePreferences
import br.com.phi.challenge.api.balance.data.remote.BalanceRepositoryImp
import br.com.phi.challenge.api.balance.domain.model.BalanceModel
import br.com.phi.challenge.api.statement.data.remote.StatementRepositoryImp
import br.com.phi.challenge.api.statement.domain.model.StatementModel
import br.com.phi.challenge.utils.toBalanceModel
import br.com.phi.challenge.utils.toStatements
import br.com.phi.challenge.view.statement.adapter.StatementAdapter
import br.com.phi.challenge.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by pcamilo on 10/01/2021.
 */

class StatementViewModel(private val balanceRepository: BalanceRepositoryImp, private val statementRepository: StatementRepositoryImp, private val balancePreference: BalancePreferences) : BaseViewModel(Application()) {

    private val statementPage = ObservableInt(0)
    val moreItemsToShow = ObservableBoolean(true)


    val hasStatements = ObservableField(VISIBLE)
    val balanceVisibility = ObservableField(balancePreference.balanceIsVisible)

    private val _balance = MutableLiveData<BalanceModel>()
    val balance : LiveData<BalanceModel> = _balance

    private val _statementId = MutableLiveData<String>()
    val statementId : LiveData<String> = _statementId

    val statementAdapter = StatementAdapter(_statementId)
    private val _statements = MutableLiveData<List<StatementModel>>()

    fun getBalance() = executeAsync(true) {
        val balanceResult = balanceRepository.getBalance()

        withContext(Dispatchers.Main) {
            when (balanceResult) {
                is Result.Success -> {
                    errorVisibility.set(GONE)
                    _balance.value = balanceResult.data?.toBalanceModel()
                }
                is Result.Error -> {
                    errorVisibility.set(VISIBLE)
                    errorMessage.value = R.string.server_error
                }
            }
        }
    }

    fun getStatements() = executeAsync(false) {
        val statementsResult = statementRepository.getStatements(offset = statementPage.get())
        withContext(Dispatchers.Main) {
            when (statementsResult) {
                is Result.Success -> {
                    errorVisibility.set(GONE)
                    hasStatements.set(VISIBLE)
                    _statements.value = statementsResult.data?.toStatements()
                }
                is Result.Error -> {
                    errorVisibility.set(VISIBLE)
                    hasStatements.set(GONE)
                    errorMessage.value = R.string.server_error
                }
            }
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        _statements.observe({owner.lifecycle}) {
            moreItemsToShow.set(it.isNotEmpty())
            loadStatements(it)
        }
    }

    fun loadMoreItems() {
        if (moreItemsToShow.get()) {
            statementPage.set(statementPage.get().inc())
            getStatements()
        }
    }

    fun updateBalanceVisibility() {
        val visibilityUpdated = if(balancePreference.balanceIsVisible == VISIBLE) GONE else VISIBLE
        balancePreference.balanceIsVisible = visibilityUpdated
        balanceVisibility.set(visibilityUpdated)
    }

    private fun loadStatements(items: List<StatementModel>) = statementAdapter.addItems(items)
}