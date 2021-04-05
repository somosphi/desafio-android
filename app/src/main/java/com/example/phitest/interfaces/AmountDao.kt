package com.example.phitest.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.phitest.model.AmountEntity

@Dao
interface AmountDao {

    @Insert
    fun insertAmount(amountEntity: AmountEntity)

    @Query("Select * from amount")
    fun getAmount(): List<AmountEntity>

    @Update
    fun updateAmount(amountEntity: AmountEntity)
}