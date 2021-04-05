package com.example.phitest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amount")
data class AmountEntity (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "show_amount") var showAmount: Boolean)