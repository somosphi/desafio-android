package com.example.pedroneryphi.model

import com.example.pedroneryphi.util.extensions.formatBalancePresentation
import com.example.pedroneryphi.util.extensions.formateDate
import com.example.pedroneryphi.util.extensions.formateDateHour

data class TransferDetail(
    var id: String,
    var amount: Int,
    var to: String,
    var description: String,
    var tType: String,
    var createdAt: String,
    var authentication: String = ""
)

data class TransferDetailPresentation(
    var id: String? = null,
    var value: String? = null,
    var name: String? = null,
    var description: String? = null,
    var type: String? = null,
    var data: String? = null,
    var authentication: String? = null
)

fun TransferDetail.toPresentation() : TransferDetailPresentation {
    return TransferDetailPresentation(
        this.id,
        this.amount.toString().formatBalancePresentation(),
        this.to,
        this.description,
        this.tType,
        this.createdAt.formateDateHour(),
        this.authentication
    )
}