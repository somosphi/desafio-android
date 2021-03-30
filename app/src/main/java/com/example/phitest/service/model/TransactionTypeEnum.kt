package com.example.phitest.service.model

enum class TransactionTypeEnum(val value: String) {
    TRANSFEROUT("Transferência enviada"),
    TRANSFERIN("Transferência recebida"),
    PIXCASHIN("Transferência Pix realizada"),
    PIXCASHOUT("Transferência Pix Enviado"),
    BANKSLIPCASHIN("Depósito por boleto")
}