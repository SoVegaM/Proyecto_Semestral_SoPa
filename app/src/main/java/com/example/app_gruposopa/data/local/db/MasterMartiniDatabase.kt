package com.example.app_gruposopa.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Base de datos local (Room sobre SQLite)
@Database(
    entities = [GuardadoEntity::class, VistoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MasterMartiniDatabase : RoomDatabase() {

    abstract fun guardadoDao(): GuardadoDao
    abstract fun vistoDao(): VistoDao

    companion object {
        @Volatile
        private var instancia: MasterMartiniDatabase? = null

        // Una sola base de datos para toda la app
        fun obtener(context: Context): MasterMartiniDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    MasterMartiniDatabase::class.java,
                    "master_martini.db"
                ).build().also { instancia = it }
            }
    }
}
