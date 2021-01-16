package me.luanhenriquer8.phitest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.luanhenriquer8.phitest.data.api.ApiService
import me.luanhenriquer8.phitest.data.models.Balance
import me.luanhenriquer8.phitest.data.models.Statement
import me.luanhenriquer8.phitest.data.models.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val apiService: ApiService) : ViewModel() {

    private val _myBalance = MutableLiveData<Balance>()
    val myBalance: LiveData<Balance> = _myBalance

    private val _myStatementList = MutableLiveData<ArrayList<Statement>>()
    val myStatementList: LiveData<ArrayList<Statement>> = _myStatementList

    private val _myStatement = MutableLiveData<Statement>()
    val myStatement: LiveData<Statement> = _myStatement

    private val _operationFailed = MutableLiveData<Boolean>()
    val operationFailed: LiveData<Boolean> = _operationFailed

    fun fetchMyBalance() {
        apiService.balance().get().enqueue(object : Callback<Balance> {
            override fun onResponse(call: Call<Balance>, response: Response<Balance>) {
                if (response.isSuccessful) _myBalance.postValue(response.body())
                else _operationFailed.postValue(true)
            }

            override fun onFailure(call: Call<Balance>, t: Throwable) {
                _operationFailed.postValue(true)
            }
        })
    }

    fun fetchStatement(offset: Int = 1) {
        apiService.statement().get(offset).enqueue(object : Callback<Transaction> {
            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                if (response.isSuccessful) _myStatementList.postValue(response.body()?.items)
                else _operationFailed.postValue(true)
            }

            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                _operationFailed.postValue(true)
            }
        })
    }

    fun fetchStatementDetail(statementId: String) {
        apiService.statement().get(statementId).enqueue(object : Callback<Statement> {
            override fun onResponse(call: Call<Statement>, response: Response<Statement>) {
                if (response.isSuccessful) _myStatement.postValue(response.body())
                else _operationFailed.postValue(true)
            }

            override fun onFailure(call: Call<Statement>, t: Throwable) {
                _operationFailed.postValue(true)
            }
        })
    }
}