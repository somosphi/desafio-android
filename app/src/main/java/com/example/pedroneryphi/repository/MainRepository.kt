package com.example.pedroneryphi.repository

import com.example.pedroneryphi.model.Balance
import com.example.pedroneryphi.model.TransferList
import com.example.pedroneryphi.network.TransferService
import io.reactivex.Observable

class MainRepository(private val transferService: TransferService) {

    fun getTransferList(limit: Int, offset: Int) : Observable<TransferList> {
        return transferService.getTransferList(limit, offset).map { response ->
            response
        }
    }

    fun getBalance() : Observable<Balance> {
        return transferService.getBalance()
    }

}