package com.example.app_gruposopa.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_gruposopa.domain.model.Configuracion

// Tabla "guardados": un video guardado por el usuario, con fecha y una nota opcional
@Entity(tableName = "guardados")
data class GuardadoEntity(
    @PrimaryKey val videoId: String,
    val fechaGuardado: Long,
    val nota: String = ""
)
