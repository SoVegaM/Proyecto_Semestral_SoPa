package com.example.app_gruposopa.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.app_gruposopa.domain.model.Configuracion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Configuracion.nombreDataStore)

// Claves de DataStore
data class ClavesPreferencias(
    val tamanoTexto: Preferences.Key<Float> = floatPreferencesKey("tamano_texto"),
    val altoContraste: Preferences.Key<Boolean> = booleanPreferencesKey("alto_contraste"),
    val textoNegrita: Preferences.Key<Boolean> = booleanPreferencesKey("texto_negrita"),
    val nombreUsuario: Preferences.Key<String> = stringPreferencesKey("nombre_usuario"),
    val detalleNegocio: Preferences.Key<String> = stringPreferencesKey("detalle_negocio"),
    val tipoNegocio: Preferences.Key<String> = stringPreferencesKey("tipo_negocio"),
    val tamanoNegocio: Preferences.Key<String> = stringPreferencesKey("tamano_negocio"),
    val intereses: Preferences.Key<Set<String>> = stringSetPreferencesKey("intereses"),
    val fotoPerfil: Preferences.Key<String> = stringPreferencesKey("foto_perfil"),
    val eventosInscritos: Preferences.Key<Set<String>> = stringSetPreferencesKey("eventos_inscritos")
)

// Ajustes y datos del perfil guardados en el celular con DataStore
class PreferencesManager(private val context: Context) {

    private val claves = ClavesPreferencias()

    val tamanoTexto: Flow<Float> = context.dataStore.data.map { pref -> pref[claves.tamanoTexto] ?: Configuracion.tamanoTextoBase }
    val altoContraste: Flow<Boolean> = context.dataStore.data.map { pref -> pref[claves.altoContraste] ?: false }
    val textoNegrita: Flow<Boolean> = context.dataStore.data.map { pref -> pref[claves.textoNegrita] ?: false }
    val nombreUsuario: Flow<String> = context.dataStore.data.map { pref -> pref[claves.nombreUsuario] ?: Configuracion.nombreInvitado }
    val detalleNegocio: Flow<String> = context.dataStore.data.map { pref -> pref[claves.detalleNegocio] ?: Configuracion.detalleNegocioVacio }
    val tipoNegocio: Flow<String?> = context.dataStore.data.map { pref -> pref[claves.tipoNegocio] }
    val tamanoNegocio: Flow<String?> = context.dataStore.data.map { pref -> pref[claves.tamanoNegocio] }
    val intereses: Flow<Set<String>?> = context.dataStore.data.map { pref -> pref[claves.intereses] }
    val fotoPerfil: Flow<String?> = context.dataStore.data.map { pref -> pref[claves.fotoPerfil] }
    val eventosInscritos: Flow<Set<String>> = context.dataStore.data.map { pref -> pref[claves.eventosInscritos] ?: emptySet() }

    suspend fun guardarAjustes(tamano: Float, contraste: Boolean, negrita: Boolean) {
        context.dataStore.edit { pref ->
            pref[claves.tamanoTexto] = tamano
            pref[claves.altoContraste] = contraste
            pref[claves.textoNegrita] = negrita
        }
    }

    suspend fun guardarNombreUsuario(nombre: String) {
        context.dataStore.edit { pref ->
            pref[claves.nombreUsuario] = nombre
        }
    }

    suspend fun guardarPerfil(nombre: String, detalle: String) {
        context.dataStore.edit { pref ->
            pref[claves.nombreUsuario] = nombre
            pref[claves.detalleNegocio] = detalle
        }
    }

    suspend fun guardarNegocio(tipo: String, tamano: String, interesesElegidos: Set<String>) {
        context.dataStore.edit { pref ->
            pref[claves.tipoNegocio] = tipo
            pref[claves.tamanoNegocio] = tamano
            pref[claves.intereses] = interesesElegidos
            pref[claves.detalleNegocio] = "$tipo - $tamano"
        }
    }

    suspend fun guardarFotoPerfil(ruta: String) {
        context.dataStore.edit { pref ->
            pref[claves.fotoPerfil] = ruta
        }
    }

    suspend fun alternarEvento(id: String) {
        context.dataStore.edit { pref ->
            val actuales = pref[claves.eventosInscritos] ?: emptySet()
            pref[claves.eventosInscritos] = if (id in actuales) actuales - id else actuales + id
        }
    }
}