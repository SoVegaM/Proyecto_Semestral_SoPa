package com.example.app_gruposopa.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException

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

    // Si el archivo no se puede leer, se parte vacío en vez de cerrar la app
    private suspend fun leer(): Preferences =
        dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .first()

    suspend fun leerGuardados(): Set<String> = leer()[Claves.GUARDADOS] ?: emptySet()

    suspend fun leerVideosVistos(): Set<String> = leer()[Claves.VIDEOS_VISTOS] ?: emptySet()

    suspend fun escribirGuardados(ids: Set<String>) = escribir { it[Claves.GUARDADOS] = ids }

    suspend fun escribirVideosVistos(ids: Set<String>) = escribir { it[Claves.VIDEOS_VISTOS] = ids }

    // Si falla la escritura, el cambio queda solo en memoria hasta la próxima vez
    private suspend fun escribir(cambio: (MutablePreferences) -> Unit) {
        try {
            dataStore.edit { cambio(it) }
        } catch (e: IOException) {
            Log.w("ContenidosLocal", "No se pudo guardar en DataStore", e)
        }
    }
}
