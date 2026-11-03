package com.example.app_gruposopa.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        val TAMANO_TEXTO = floatPreferencesKey("tamano_texto")
        val ALTO_CONTRASTE = booleanPreferencesKey("alto_contraste")
        val TEXTO_NEGRITA = booleanPreferencesKey("texto_negrita")
        val NOMBRE_USUARIO = stringPreferencesKey("nombre_usuario")
    }

    val tamanoTexto: Flow<Float> = context.dataStore.data.map { pref -> pref[TAMANO_TEXTO] ?: 16f }
    val altoContraste: Flow<Boolean> = context.dataStore.data.map { pref -> pref[ALTO_CONTRASTE] ?: false }
    val textoNegrita: Flow<Boolean> = context.dataStore.data.map { pref -> pref[TEXTO_NEGRITA] ?: false }

    // Obtener el nombre guardado (por defecto "Carla Fuentes")
    val nombreUsuario: Flow<String> = context.dataStore.data.map { pref ->
        pref[NOMBRE_USUARIO] ?: "Carla Fuentes"
    }

    suspend fun guardarAjustes(tamano: Float, contraste: Boolean, negrita: Boolean) {
        context.dataStore.edit { pref ->
            pref[TAMANO_TEXTO] = tamano
            pref[ALTO_CONTRASTE] = contraste
            pref[TEXTO_NEGRITA] = negrita
        }
    }

    // Guardar el nombre del usuario
    suspend fun guardarNombreUsuario(nombre: String) {
        context.dataStore.edit { pref ->
            pref[NOMBRE_USUARIO] = nombre
        }
    }
}