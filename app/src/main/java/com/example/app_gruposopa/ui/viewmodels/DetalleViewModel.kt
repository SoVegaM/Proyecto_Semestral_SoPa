package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.model.Curso
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetalleViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) : ViewModel() {

    private val _video = MutableStateFlow<ContenidoMasterMartini?>(null)
    val video: StateFlow<ContenidoMasterMartini?> = _video.asStateFlow()

    private val _videoIdActual = MutableStateFlow("")

    // Cambia si cambia el video o la lista de guardados
    val esGuardado: StateFlow<Boolean> = combine(_videoIdActual, repository.guardados) { id, guardados ->
        id.isNotEmpty() && id in guardados
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun cargarVideo(videoId: String) {
        _videoIdActual.value = videoId
        _video.value = repository.obtenerVideoPorId(videoId)
    }

    // Se llama cuando el video parte o se abre en YouTube (cuenta para el progreso del curso)
    fun marcarComoVisto() {
        val id = _videoIdActual.value
        if (id.isNotEmpty()) {
            repository.marcarComoVisto(id)
        }
    }

    // Aviso de abajo al guardar o quitar (null = no se ve)
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()
    private var trabajoMensaje: Job? = null

    fun toggleGuardado() {
        val id = _videoIdActual.value
        if (id.isNotEmpty()) {
            repository.toggleGuardado(id)
            mostrarMensaje(if (repository.esGuardado(id)) "Guardado en Mis guardados" else "Quitado de Mis guardados")
        }
    }

    // Se muestra 2 segundos
    private fun mostrarMensaje(texto: String) {
        trabajoMensaje?.cancel()
        trabajoMensaje = viewModelScope.launch {
            _mensaje.value = texto
            delay(2000)
            _mensaje.value = null
        }
    }

    fun obtenerCursoAsociado(): Curso? {
        val id = _videoIdActual.value
        return if (id.isNotEmpty()) repository.obtenerCursoPorVideoId(id) else null
    }
}
