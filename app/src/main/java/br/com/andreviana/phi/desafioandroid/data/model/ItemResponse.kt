package br.com.andreviana.phi.desafioandroid.data.model

import java.util.*

data class ItemResponse(
    val amount: Int,
    val bankName: String,
    val createdAt: Date,
    val authentication: String,
    val description: String,
    val from: String,
    val id: String,
    val tType: String,
    val to: String?
)

fun ItemResponse.mapperToStatementDetail() =
    StatementViewDetail(description, amount, to, from, bankName, createdAt, authentication)

fun ItemResponse.mapperToStatementList() =
    StatementViewList(id, description, amount, to, from, createdAt, tType)
