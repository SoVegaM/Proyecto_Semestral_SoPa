package com.example.app_gruposopa.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.contenidosDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "contenidos_master_martini"
)

/**
 * Guarda en DataStore los ids de los videos guardados y de los vistos.
 */
class ContenidosLocalDataSource(context: Context) {

    private val dataStore = context.applicationContext.contenidosDataStore

    private object Claves {
        val GUARDADOS = stringSetPreferencesKey("guardados")
        val VIDEOS_VISTOS = stringSetPreferencesKey("videos_vistos")
    }

    suspend fun leerGuardados(): Set<String> =
        dataStore.data.first()[Claves.GUARDADOS] ?: emptySet()

    suspend fun leerVideosVistos(): Set<String> =
        dataStore.data.first()[Claves.VIDEOS_VISTOS] ?: emptySet()

    suspend fun escribirGuardados(ids: Set<String>) {
        dataStore.edit { it[Claves.GUARDADOS] = ids }
    }

    suspend fun escribirVideosVistos(ids: Set<String>) {
        dataStore.edit { it[Claves.VIDEOS_VISTOS] = ids }
    }
}
