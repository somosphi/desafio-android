package br.com.andreviana.phi.desafioandroid.data.model

import java.util.*

data class StatementViewList(
    val id: String,
    val description: String,
    val amount: Int,
    val to: String?,
    val createdAt: Date,
    val transactionType: String
)