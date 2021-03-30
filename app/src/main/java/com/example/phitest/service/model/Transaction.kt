package com.example.phitest.service.model

data class Transaction(val items: List<Items>) {

    data class Items(
        val createdAt: String,
        val id: String,
        val amount: Double,
        val to: String,
        val description: String,
        val tType: String)

}