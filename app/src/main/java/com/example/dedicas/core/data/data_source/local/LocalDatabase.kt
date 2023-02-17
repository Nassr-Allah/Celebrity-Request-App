package com.example.dedicas.core.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dedicas.core.data.models.AuthToken

@Database(entities = [AuthToken::class], version = 2)
abstract class LocalDatabase : RoomDatabase() {

    abstract val dao: DatabaseDao

}