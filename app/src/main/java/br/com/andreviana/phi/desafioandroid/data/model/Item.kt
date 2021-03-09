package br.com.andreviana.phi.desafioandroid.data.model

import java.util.*

data class Item(
        val amount: Int,
        val bankName: String,
        val createdAt: Date,
        val description: String,
        val from: String,
        val id: String,
        val tType: String,
        val to: String
)