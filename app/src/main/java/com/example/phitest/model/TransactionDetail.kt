package com.example.phitest.model

data class TransactionDetail(
        val amount: Int,
        val id: String,
        val authentication: String,
        val tType: String,
        val createdAt: String,
        val to: String,
        val from: String,
        val description: String,
        val bankName: String
)