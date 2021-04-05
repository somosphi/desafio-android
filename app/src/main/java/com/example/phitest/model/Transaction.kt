package com.example.phitest.model

import java.io.Serializable

data class Transaction(val items: ArrayList<Items?>) {

    data class Items(
        val createdAt: String,
        val id: String,
        val amount: Double,
        val to: String,
        val from: String,
        val description: String,
        val tType: String,
        val bankName: String) : Serializable

}