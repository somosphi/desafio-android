package com.example.phitest.repository

import androidx.lifecycle.liveData
import com.example.phitest.interfaces.TransactionService
import com.example.phitest.util.VsFunctions
import com.example.phitest.view.viewmodel.Result
import java.net.ConnectException

class TransactionDetailRepository(private val service: TransactionService) {

    fun getDetailById(id: String) = liveData {
        try {
            val response = service.myStatementDetail(VsFunctions.TOKEN, id)
            if(response.isSuccessful){
                emit(Result.Success(data = response.body()))
            }else{
                emit(Result.Error(exception = Exception(VsFunctions.TRANSACTION_API_EXCEPTION_MSG)))
            }
        } catch (e: ConnectException) {
            emit(Result.Error(exception = Exception(VsFunctions.TRANSACTION_API_EXCEPTION_MSG)))
        }
        catch (e: Exception) {
            emit(Result.Error(exception = e))
        }
    }

    fun myBalance(token: String) = liveData {
        try {
            val response = service.myBalance(token)
            if(response.isSuccessful){
                emit(Result.Success(data = response.body()))
            }else{
                emit(Result.Error(exception = Exception(VsFunctions.TRANSACTION_API_EXCEPTION_MSG)))
            }
        } catch (e: ConnectException) {
            emit(Result.Error(exception = Exception(VsFunctions.TRANSACTION_API_EXCEPTION_MSG)))
        }
        catch (e: Exception) {
            emit(Result.Error(exception = e))
        }
    }

    fun myStatements(token: String, limit: Int, offset: Int) = liveData {
        try {
            val response = service.myStatements(token, limit, offset)
            if(response.isSuccessful){
                emit(Result.Success(data = response.body()))
            }else{
                emit(Result.Error(exception = Exception(VsFunctions.TRANSACTION_API_EXCEPTION_MSG)))
            }
        } catch (e: ConnectException) {
            emit(Result.Error(exception = Exception(VsFunctions.TRANSACTION_API_EXCEPTION_MSG)))
        }
        catch (e: Exception) {
            emit(Result.Error(exception = e))
        }
    }
}