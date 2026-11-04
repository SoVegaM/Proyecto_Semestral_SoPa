package com.example.app_gruposopa.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// CRUD de Mis guardados
@Dao
interface GuardadoDao {

    // Create. IGNORE para no borrar la nota si ya estaba guardado
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(guardado: GuardadoEntity)

    // Read, los más nuevos primero
    @Query("SELECT * FROM guardados ORDER BY fechaGuardado DESC")
    fun observarTodos(): Flow<List<GuardadoEntity>>

    // Update
    @Query("UPDATE guardados SET nota = :nota WHERE videoId = :videoId")
    suspend fun actualizarNota(videoId: String, nota: String)

    // Delete
    @Query("DELETE FROM guardados WHERE videoId = :videoId")
    suspend fun eliminar(videoId: String)
}
