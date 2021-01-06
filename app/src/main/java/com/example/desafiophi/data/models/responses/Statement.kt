package com.example.desafiophi.data.models.responses


import com.google.gson.annotations.SerializedName

data class Statement(
    val items: List<Item>
) {
    data class Item(
        val amount: Int,
        val createdAt: String,
        val description: String,
        val id: String,
        val tType: String,
        val to: String
    )
}