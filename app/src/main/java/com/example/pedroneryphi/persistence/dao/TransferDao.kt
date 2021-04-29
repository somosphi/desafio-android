package com.example.pedroneryphi.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pedroneryphi.model.TransferDetailDao

@Dao
interface TransferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransfer(transferDao: TransferDetailDao)

    @Query("SELECT * FROM transfers WHERE id = :id_")
    fun getTransfer(id_: Long): TransferDetailDao

    @Query("SELECT * FROM transfers")
    fun getTransferList(): Array<TransferDetailDao>
}