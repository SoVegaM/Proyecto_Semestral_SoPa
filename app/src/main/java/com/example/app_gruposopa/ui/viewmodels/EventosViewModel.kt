package com.example.app_gruposopa.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.local.PreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class Evento(val id: String, val titulo: String, val detalle: String)

// Vista 7 - Las inscripciones se guardan en el celular
class EventosViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)

    val eventos = listOf(
        Evento("taller_templado", "Taller de templado de chocolate", "15 dic - Presencial - Centro Técnico"),
        Evento("demo_heladeria", "Demo de heladería artesanal", "28 dic - Online vía Zoom")
    )

    val inscritos: StateFlow<Set<String>> = preferencesManager.eventosInscritos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun alternarInscripcion(id: String) {
        viewModelScope.launch { preferencesManager.alternarEvento(id) }
    }
}
