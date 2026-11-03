package com.example.app_gruposopa.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.local.PreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccesibilidadViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)

    val tamanoTexto = preferencesManager.tamanoTexto.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), 16f
    )

    val altoContraste = preferencesManager.altoContraste.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    val textoNegrita = preferencesManager.textoNegrita.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    fun guardarCambios(tamano: Float, contraste: Boolean, negrita: Boolean) {
        viewModelScope.launch {
            preferencesManager.guardarAjustes(tamano, contraste, negrita)
        }
    }
}