package com.henrique.desafio_android.presenter.movimentation.detail

import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.domain.home.GetMyStatementInteractor
import com.henrique.desafio_android.presenter.model.BaseViewModel
import com.henrique.desafio_android.presenter.model.RequestState
import com.henrique.desafio_android.utils.formatCurrency
import com.henrique.desafio_android.utils.getPerson
import com.henrique.desafio_android.utils.getPersonType
import com.henrique.desafio_android.utils.getDateTimeFormatted

class MovimentationDetailViewModel(
    private val myStatementInteractor: GetMyStatementInteractor
) : BaseViewModel() {

    val movimentationId = MutableLiveData<String>()
    val movimentationType = MutableLiveData<String>()
    val movimentationAmount = MutableLiveData<String>()
    val movimentationPersonType = MutableLiveData<String>()
    val movimentationPerson = MutableLiveData<String>()
    val movimentationBank = MutableLiveData<String>()
    val movimentationDate = MutableLiveData<String>()
    val movimentationAuthentication = MutableLiveData<String>()
    val shouldCloseActivity = MutableLiveData(false)

    init {
        requestState.addSource(myStatementInteractor.requestStateDetail) {
            requestState.value = it

            when (it) {
                is RequestState.Success -> {
                    movimentationType.postValue(it.result.description)
                    movimentationAmount.postValue(it.result.amount.formatCurrency())
                    movimentationPersonType.postValue(
                        getPersonType(
                            it.result.to.orEmpty()
                        )
                    )
                    movimentationPerson.postValue(
                        getPerson(
                            it.result.to.orEmpty(),
                            it.result.from.orEmpty()
                        )
                    )
                    movimentationBank.postValue(it.result.bankName.orEmpty())
                    movimentationDate.postValue(it.result.createdAt.getDateTimeFormatted())
                    movimentationAuthentication.postValue(it.result.authentication.orEmpty())
                }
                else -> { /* Intentionally left empty */
                }
            }
        }
    }

    fun onBackClick() {
        shouldCloseActivity.postValue(true)
    }

    fun onShareClick() {

    }

    fun getStatementDetail() {
        movimentationId.value?.let {
            myStatementInteractor.getStatementDetail(it)
        }
    }
}
