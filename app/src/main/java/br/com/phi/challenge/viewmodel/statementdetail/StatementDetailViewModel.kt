package br.com.phi.challenge.viewmodel.statementdetail

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.phi.challenge.R
import br.com.phi.challenge.api.Result
import br.com.phi.challenge.api.statement.data.remote.StatementRepositoryImp
import br.com.phi.challenge.api.statement.domain.model.StatementModel
import br.com.phi.challenge.utils.toStatementDetailModel
import br.com.phi.challenge.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by pcamilo on 10/01/2021.
 */
class StatementDetailViewModel(private val statementRepository: StatementRepositoryImp) : BaseViewModel(Application()) {

    private val _statementDetail = MutableLiveData<StatementModel>()
    val statementDetail : LiveData<StatementModel> = _statementDetail

    fun getStatementDetail(statementId: String) = executeAsync(true) {
        val statementDetailResult = statementRepository.getStatementDetail(statementId)

        withContext(Dispatchers.Main) {
            when(statementDetailResult) {
                is Result.Success -> {
                    errorVisibility.set(View.GONE)
                    _statementDetail.value = statementDetailResult.data?.toStatementDetailModel()
                }
                is Result.Error -> {
                    errorVisibility.set(View.VISIBLE)
                    errorMessage.value = R.string.server_error
                }
            }
        }
    }
}