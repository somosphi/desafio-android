package com.example.pedroneryphi.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pedroneryphi.model.TransferDetailDao
import com.example.pedroneryphi.persistence.dao.TransferDao

@Database(entities = [TransferDetailDao::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun jokeDao(): TransferDao
}