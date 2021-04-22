package com.henrique.desafio_android.viewmodel.movimentationdetail

import androidx.lifecycle.MutableLiveData
import com.henrique.desafio_android.domain.repository.GetMovimentationInteractor
import com.henrique.desafio_android.domain.model.RequestState
import com.henrique.desafio_android.domain.utils.formatCurrency
import com.henrique.desafio_android.domain.utils.getPerson
import com.henrique.desafio_android.domain.utils.getPersonType
import com.henrique.desafio_android.domain.utils.getDateTimeFormatted
import com.henrique.desafio_android.viewmodel.BaseViewModel

class MovimentationDetailViewModel(
    private val movimentationInteractor: GetMovimentationInteractor
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
    val shouldShareActivity = MutableLiveData(false)

    init {
        requestState.addSource(movimentationInteractor.requestStateDetail) {
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
        shouldShareActivity.postValue(true)
    }

    fun getStatementDetail() {
        movimentationId.value?.let {
            movimentationInteractor.getMovimentationDetail(it)
        }
    }
}
