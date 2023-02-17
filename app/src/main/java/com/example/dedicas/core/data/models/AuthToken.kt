package com.example.dedicas.core.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthToken(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo val expiry: String = "",
    @ColumnInfo val token: String = ""
)
