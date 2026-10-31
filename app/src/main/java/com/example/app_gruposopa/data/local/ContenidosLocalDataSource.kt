package com.example.app_gruposopa.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
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

// DataStore donde la app guardaba antes los guardados y los vistos.
// Ahora eso está en Room: esto solo se usa una vez para pasar los datos antiguos y borrarlos
class ContenidosLocalDataSource(context: Context) {

    private val dataStore = context.applicationContext.contenidosDataStore

    private object Claves {
        val GUARDADOS = stringSetPreferencesKey("guardados")
        val VIDEOS_VISTOS = stringSetPreferencesKey("videos_vistos")
    }

    // Si no se puede leer el archivo se parte de cero, para que no se caiga la app
    private suspend fun leer(): Preferences =
        dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .first()

    suspend fun leerGuardados(): Set<String> = leer()[Claves.GUARDADOS] ?: emptySet()

    suspend fun leerVideosVistos(): Set<String> = leer()[Claves.VIDEOS_VISTOS] ?: emptySet()

    suspend fun borrarDatosAntiguos() {
        try {
            dataStore.edit {
                it.remove(Claves.GUARDADOS)
                it.remove(Claves.VIDEOS_VISTOS)
            }
        } catch (e: IOException) {
            Log.w("ContenidosLocal", "No se pudieron borrar los datos antiguos", e)
        }
    }
}
