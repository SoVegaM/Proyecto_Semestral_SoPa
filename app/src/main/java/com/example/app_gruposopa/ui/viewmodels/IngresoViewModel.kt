package com.example.app_gruposopa.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Vista 2 - Valida cada campo al escribir y todos de nuevo al enviar
class IngresoViewModel : ViewModel() {

    private val _esRegistro = MutableStateFlow(false)
    val esRegistro: StateFlow<Boolean> = _esRegistro.asStateFlow()

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()
    private val _errorNombre = MutableStateFlow<String?>(null)
    val errorNombre: StateFlow<String?> = _errorNombre.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo.asStateFlow()
    private val _errorCorreo = MutableStateFlow<String?>(null)
    val errorCorreo: StateFlow<String?> = _errorCorreo.asStateFlow()

    private val _clave = MutableStateFlow("")
    val clave: StateFlow<String> = _clave.asStateFlow()
    private val _errorClave = MutableStateFlow<String?>(null)
    val errorClave: StateFlow<String?> = _errorClave.asStateFlow()

    fun cambiarModo(registro: Boolean) {
        _esRegistro.value = registro
        // Lo que ya está escrito se sigue validando, lo vacío no marca error todavía
        _errorNombre.value = if (_nombre.value.isEmpty()) null else validarNombre(_nombre.value)
        _errorCorreo.value = if (_correo.value.isEmpty()) null else validarCorreo(_correo.value)
        _errorClave.value = if (_clave.value.isEmpty()) null else validarClave(_clave.value)
    }

    fun onNombreChange(nuevo: String) {
        _nombre.value = nuevo
        _errorNombre.value = validarNombre(nuevo)
    }

    fun onCorreoChange(nuevo: String) {
        _correo.value = nuevo
        _errorCorreo.value = validarCorreo(nuevo)
    }

    fun onClaveChange(nueva: String) {
        _clave.value = nueva
        _errorClave.value = validarClave(nueva)
    }

    // Devuelve el nombre a guardar, o null si hay errores
    fun enviar(): String? {
        _errorNombre.value = if (_esRegistro.value) validarNombre(_nombre.value) else null
        _errorCorreo.value = validarCorreo(_correo.value)
        _errorClave.value = validarClave(_clave.value)

        val hayErrores = _errorNombre.value != null || _errorCorreo.value != null || _errorClave.value != null
        return when {
            hayErrores -> null
            _esRegistro.value -> _nombre.value.trim()
            else -> _correo.value.trim().substringBefore("@").replaceFirstChar { it.uppercase() }
        }
    }

    private fun validarNombre(nombre: String): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre"
        nombre.trim().length < 3 -> "Mínimo 3 caracteres"
        else -> null
    }

    private fun validarCorreo(correo: String): String? = when {
        correo.isBlank() -> "Ingresa tu correo"
        !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> "Correo no válido"
        else -> null
    }

    private fun validarClave(clave: String): String? = when {
        clave.isBlank() -> "Ingresa tu contraseña"
        clave.length < 6 -> "Mínimo 6 caracteres"
        else -> null
    }
}
