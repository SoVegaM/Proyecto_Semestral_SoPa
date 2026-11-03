package com.example.app_gruposopa.ui.viewmodels

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.local.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

// Vista 8 - Datos del perfil y validación del cuadro de editar
class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)

    val nombre: StateFlow<String> = preferencesManager.nombreUsuario
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
    val detalleNegocio: StateFlow<String> = preferencesManager.detalleNegocio
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
    val fotoPerfil: StateFlow<String?> = preferencesManager.fotoPerfil
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _editando = MutableStateFlow(false)
    val editando: StateFlow<Boolean> = _editando.asStateFlow()

    private val _nombreEditado = MutableStateFlow("")
    val nombreEditado: StateFlow<String> = _nombreEditado.asStateFlow()
    private val _errorNombre = MutableStateFlow<String?>(null)
    val errorNombre: StateFlow<String?> = _errorNombre.asStateFlow()

    private val _detalleEditado = MutableStateFlow("")
    val detalleEditado: StateFlow<String> = _detalleEditado.asStateFlow()
    private val _errorDetalle = MutableStateFlow<String?>(null)
    val errorDetalle: StateFlow<String?> = _errorDetalle.asStateFlow()

    // Aviso de abajo si no se pudo cambiar la foto (null = no se ve)
    private val _errorFoto = MutableStateFlow<String?>(null)
    val errorFoto: StateFlow<String?> = _errorFoto.asStateFlow()

    fun abrirEdicion() {
        _nombreEditado.value = nombre.value
        _detalleEditado.value = detalleNegocio.value
        _errorNombre.value = null
        _errorDetalle.value = null
        _editando.value = true
    }

    fun cerrarEdicion() {
        _editando.value = false
    }

    fun onNombreChange(nuevo: String) {
        _nombreEditado.value = nuevo
        _errorNombre.value = validarNombre(nuevo)
    }

    fun onDetalleChange(nuevo: String) {
        _detalleEditado.value = nuevo
        _errorDetalle.value = validarDetalle(nuevo)
    }

    fun guardarEdicion() {
        _errorNombre.value = validarNombre(_nombreEditado.value)
        _errorDetalle.value = validarDetalle(_detalleEditado.value)
        if (_errorNombre.value != null || _errorDetalle.value != null) return
        viewModelScope.launch {
            preferencesManager.guardarPerfil(_nombreEditado.value.trim(), _detalleEditado.value.trim())
            _editando.value = false
        }
    }

    // Copia la foto a la app: el permiso para leer la original se pierde al cerrarla
    fun cambiarFoto(uri: Uri) {
        viewModelScope.launch {
            val anterior = preferencesManager.fotoPerfil.first()
            val copia = withContext(Dispatchers.IO) { copiarFoto(uri) }
            if (copia == null) {
                _errorFoto.value = "No se pudo cambiar la foto"
                return@launch
            }
            preferencesManager.guardarFotoPerfil(copia.path)
            if (anterior != null) {
                withContext(Dispatchers.IO) { File(anterior).delete() }
            }
        }
    }

    fun errorFotoMostrado() {
        _errorFoto.value = null
    }

    private fun copiarFoto(uri: Uri): File? {
        val app = getApplication<Application>()
        // Nombre nuevo cada vez, si no Coil seguiría mostrando la foto anterior
        val destino = File(app.filesDir, "foto_perfil_${System.currentTimeMillis()}.jpg")
        return try {
            val entrada = app.contentResolver.openInputStream(uri) ?: return null
            entrada.use { lectura ->
                destino.outputStream().use { escritura -> lectura.copyTo(escritura) }
            }
            destino
        } catch (e: Exception) {
            destino.delete()
            null
        }
    }

    private fun validarNombre(nombre: String): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre"
        nombre.trim().length < 3 -> "Mínimo 3 caracteres"
        else -> null
    }

    private fun validarDetalle(detalle: String): String? =
        if (detalle.isBlank()) "Ingresa el tipo y tamaño de tu negocio" else null
}
