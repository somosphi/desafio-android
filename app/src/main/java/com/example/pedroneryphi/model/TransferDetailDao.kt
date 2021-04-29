package com.example.pedroneryphi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfers")
data class TransferDetailDao(
    @PrimaryKey
    val idJoke: Long,
    val id: String,
    val text: String
)