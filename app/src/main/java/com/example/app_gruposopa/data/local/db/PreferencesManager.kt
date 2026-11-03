package com.example.app_gruposopa.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings_preferences")

// Ajustes y datos del perfil guardados en el celular con DataStore
class PreferencesManager(private val context: Context) {

    companion object {
        val TAMANO_TEXTO = floatPreferencesKey("tamano_texto")
        val ALTO_CONTRASTE = booleanPreferencesKey("alto_contraste")
        val TEXTO_NEGRITA = booleanPreferencesKey("texto_negrita")
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
        val DETALLE_NEGOCIO = stringPreferencesKey("detalle_negocio")
        val TIPO_NEGOCIO = stringPreferencesKey("tipo_negocio")
        val TAMANO_NEGOCIO = stringPreferencesKey("tamano_negocio")
        val INTERESES = stringSetPreferencesKey("intereses")
        val FOTO_PERFIL = stringPreferencesKey("foto_perfil")
        val EVENTOS_INSCRITOS = stringSetPreferencesKey("eventos_inscritos")
    }

    val tamanoTexto: Flow<Float> = context.dataStore.data.map { pref -> pref[TAMANO_TEXTO] ?: 16f }
    val altoContraste: Flow<Boolean> = context.dataStore.data.map { pref -> pref[ALTO_CONTRASTE] ?: false }
    val textoNegrita: Flow<Boolean> = context.dataStore.data.map { pref -> pref[TEXTO_NEGRITA] ?: false }
    val nombreUsuario: Flow<String> = context.dataStore.data.map { pref -> pref[NOMBRE_USUARIO] ?: "Invitado" }
    val detalleNegocio: Flow<String> = context.dataStore.data.map { pref -> pref[DETALLE_NEGOCIO] ?: "Sin datos del negocio" }
    val tipoNegocio: Flow<String?> = context.dataStore.data.map { pref -> pref[TIPO_NEGOCIO] }
    val tamanoNegocio: Flow<String?> = context.dataStore.data.map { pref -> pref[TAMANO_NEGOCIO] }
    val intereses: Flow<Set<String>?> = context.dataStore.data.map { pref -> pref[INTERESES] }
    val fotoPerfil: Flow<String?> = context.dataStore.data.map { pref -> pref[FOTO_PERFIL] }
    val eventosInscritos: Flow<Set<String>> = context.dataStore.data.map { pref -> pref[EVENTOS_INSCRITOS] ?: emptySet() }

    suspend fun guardarAjustes(tamano: Float, contraste: Boolean, negrita: Boolean) {
        context.dataStore.edit { pref ->
            pref[TAMANO_TEXTO] = tamano
            pref[ALTO_CONTRASTE] = contraste
            pref[TEXTO_NEGRITA] = negrita
        }
    }

    suspend fun guardarNombreUsuario(nombre: String) {
        context.dataStore.edit { pref ->
            pref[NOMBRE_USUARIO] = nombre
        }
    }

    suspend fun guardarPerfil(nombre: String, detalle: String) {
        context.dataStore.edit { pref ->
            pref[NOMBRE_USUARIO] = nombre
            pref[DETALLE_NEGOCIO] = detalle
        }
    }

    suspend fun guardarNegocio(tipo: String, tamano: String, interesesElegidos: Set<String>) {
        context.dataStore.edit { pref ->
            pref[TIPO_NEGOCIO] = tipo
            pref[TAMANO_NEGOCIO] = tamano
            pref[INTERESES] = interesesElegidos
            pref[DETALLE_NEGOCIO] = "$tipo - $tamano"
        }
    }

    suspend fun guardarFotoPerfil(ruta: String) {
        context.dataStore.edit { pref ->
            pref[FOTO_PERFIL] = ruta
        }
    }

    suspend fun alternarEvento(id: String) {
        context.dataStore.edit { pref ->
            val actuales = pref[EVENTOS_INSCRITOS] ?: emptySet()
            pref[EVENTOS_INSCRITOS] = if (id in actuales) actuales - id else actuales + id
        }
    }
}
