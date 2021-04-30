package com.example.pedroneryphi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfers")
data class TransferDetailDao(
    @PrimaryKey
    var id: String,
    var amount: Int,
    var to: String,
    var description: String,
    var tType: String,
    var createdAt: String,
    var authentication: String
)