package com.example.desafiophi.data.models.responses


data class StatementDetail(
    val amount: Int,
    val authentication: String,
    val createdAt: String,
    val description: String,
    val id: String,
    val tType: String,
    val to: String
)