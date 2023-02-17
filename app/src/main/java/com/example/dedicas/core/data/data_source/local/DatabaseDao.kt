package com.example.dedicas.core.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dedicas.core.data.models.AuthToken

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM authToken")
    suspend fun getCachedToken(): AuthToken?

    @Insert
    suspend fun insertTokenInDb(authToken: AuthToken)

    @Query("DELETE FROM authToken")
    suspend fun deleteCachedToken()

}