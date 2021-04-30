package com.example.pedroneryphi.model

import androidx.room.Entity

@Entity(tableName = "transfers")
data class TransferDetailDao(
    var id: String,
    var amount: Int,
    var to: String,
    var description: String,
    var tType: String,
    var createdAt: String,
    var authentication: String
)