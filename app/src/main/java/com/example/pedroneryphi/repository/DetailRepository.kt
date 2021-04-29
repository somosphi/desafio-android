package com.example.pedroneryphi.repository

import com.example.pedroneryphi.model.TransferDetail
import com.example.pedroneryphi.model.TransferList
import com.example.pedroneryphi.network.TransferService
import com.example.pedroneryphi.persistence.dao.TransferDao
import io.reactivex.Observable

class DetailRepository(private val transferService: TransferService,
                       private val transferDao: TransferDao) {

    fun getTransferDetail(id: String) : Observable<TransferDetail> {
        return transferService.getTransfer(id)
    }

}