package com.example.app_gruposopa.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VistoDao {

    // REPLACE: si ya estaba, se actualiza la fecha (sirve para "Seguir viendo")
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrar(visto: VistoEntity)

    // Los más recientes primero
    @Query("SELECT * FROM vistos ORDER BY fechaVisto DESC")
    fun observarTodos(): Flow<List<VistoEntity>>
}
