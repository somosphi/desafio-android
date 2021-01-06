package com.example.desafiophi.data.models.responses


data class StatementItem(
    val amount: Int,
    val createdAt: String,
    val description: String,
    val id: String,
    val tType: String,
    val to: String
)