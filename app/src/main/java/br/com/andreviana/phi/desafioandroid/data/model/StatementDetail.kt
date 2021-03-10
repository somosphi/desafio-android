package br.com.andreviana.phi.desafioandroid.data.model

import java.util.*

data class StatementDetail(
        val amount: Int,
        val authentication: String,
        val createdAt: Date,
        val bankName: String?,
        val description: String,
        val id: String,
        val tType: String,
        val to: String
)