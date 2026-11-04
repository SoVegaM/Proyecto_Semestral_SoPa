package com.example.app_gruposopa.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.local.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.app_gruposopa.domain.model.OpcionesDeNegocio

// Vista 3 - Parte con lo último guardado y exige al menos un interés
class PerfilNegocioViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)

    private val _tipo = MutableStateFlow(OpcionesDeNegocio.tipos.first())
    val tipo: StateFlow<String> = _tipo.asStateFlow()

    private val _tamano = MutableStateFlow(OpcionesDeNegocio.tamanos[1])
    val tamano: StateFlow<String> = _tamano.asStateFlow()

    private val _intereses = MutableStateFlow(setOf(OpcionesDeNegocio.intereses[0], OpcionesDeNegocio.intereses[1]))
    val intereses: StateFlow<Set<String>> = _intereses.asStateFlow()

    private val _errorIntereses = MutableStateFlow<String?>(null)
    val errorIntereses: StateFlow<String?> = _errorIntereses.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesManager.tipoNegocio.first()?.let { _tipo.value = it }
            preferencesManager.tamanoNegocio.first()?.let { _tamano.value = it }
            preferencesManager.intereses.first()?.let { _intereses.value = it }
        }
    }

    fun seleccionarTipo(nuevo: String) {
        _tipo.value = nuevo
    }

    fun seleccionarTamano(nuevo: String) {
        _tamano.value = nuevo
    }

    fun alternarInteres(interes: String) {
        val actuales = _intereses.value
        _intereses.value = if (interes in actuales) actuales - interes else actuales + interes
        _errorIntereses.value = validarIntereses()
    }

    fun guardar(alTerminar: () -> Unit) {
        _errorIntereses.value = validarIntereses()
        if (_errorIntereses.value != null) return
        viewModelScope.launch {
            preferencesManager.guardarNegocio(_tipo.value, _tamano.value, _intereses.value)
            alTerminar()
        }
    }

    private fun validarIntereses(): String? =
        if (_intereses.value.isEmpty()) "Elige al menos un interés" else null
}
