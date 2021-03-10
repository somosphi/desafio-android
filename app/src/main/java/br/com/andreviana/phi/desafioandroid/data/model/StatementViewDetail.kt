package br.com.andreviana.phi.desafioandroid.data.model

import java.util.*

data class StatementViewDetail(
        val description: String,
        val amount: Int,
        val to: String?,
        val bankName: String?,
        val createdAt: Date,
        val authentication: String
)