package com.example.app_gruposopa.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla "vistos": cada video que el usuario reprodujo y la última vez que lo vio
@Entity(tableName = "vistos")
data class VistoEntity(
    @PrimaryKey val videoId: String,
    val fechaVisto: Long
)
