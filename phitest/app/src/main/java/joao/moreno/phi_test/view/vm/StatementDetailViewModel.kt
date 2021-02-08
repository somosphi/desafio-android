package joao.moreno.phi_test.view.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import joao.moreno.phi_test.repository.PhiServiceApi
import joao.moreno.phi_test.model.statement.StatementDetaiVO

class StatementDetailViewModel(transactionID : String) : ViewModel() {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    val statementDetailLiveData : MutableLiveData<StatementDetaiVO> by lazy {
        MutableLiveData<StatementDetaiVO>()
    }
    val error: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val loading : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        loading.postValue(true)
        getStatementDetail(transactionID)
    }

    private fun getStatementDetail(transactionID: String) {
        compositeDisposable.add(
            PhiServiceApi().getStatementDetail(transactionID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ statementDetail ->
                    loading.postValue(false)
                    statementDetailLiveData.postValue(statementDetail)
                }, {
                    error.postValue(true)
                })
        )
    }

}