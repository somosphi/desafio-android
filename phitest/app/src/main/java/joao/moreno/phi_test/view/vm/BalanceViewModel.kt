package joao.moreno.phi_test.view.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import joao.moreno.phi_test.PhiApplication
import joao.moreno.phi_test.repository.PhiServiceApi
import joao.moreno.phi_test.model.balance.BalanceVO
import joao.moreno.phi_test.model.statement.StatementVO
import joao.moreno.phi_test.utils.Constants.Companion.LIMIT_CALL_STATEMENT


class BalanceViewModel : ViewModel() {

    val balanceLiveData: MutableLiveData<BalanceVO> by lazy {
        MutableLiveData<BalanceVO>()
    }
    val statementLiveData: MutableLiveData<StatementVO> by lazy {
        MutableLiveData<StatementVO>()
    }
    val error: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val stateVisibilityBalance: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    var sharedPreferences = PhiApplication.instance.applicationContext.getSharedPreferences(
        "key_shared",
        Context.MODE_PRIVATE
    )

    var offset : Int = 1

    init {
        getBalance()
        getStatement(offset)
        getStateVisibilityBalance()
    }

    private fun getBalance() {
        compositeDisposable.add(
            PhiServiceApi().getBalance().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ balance ->
                    balanceLiveData.postValue(balance)
                }, {
                    error.postValue(true)
                })
        )
    }

    fun getStatement(offset: Int) {
        compositeDisposable.add(
            PhiServiceApi().getStatement(LIMIT_CALL_STATEMENT, "$offset")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ statement ->
                    statementLiveData.postValue(statement)
                }, {
                    error.postValue(true)
                })
        )
    }

    fun saveStateVisibilityBalance(isVisible: Boolean) {
        val editor = sharedPreferences.edit()
        editor!!.putBoolean(
           "state_visibility_balance",
            isVisible
        )
        editor.apply()
    }

    fun getStateVisibilityBalance() {
        stateVisibilityBalance.postValue(
            sharedPreferences.getBoolean(
                "state_visibility_balance",
                true
            )
        )

    }
}