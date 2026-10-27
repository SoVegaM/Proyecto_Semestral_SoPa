package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.model.Curso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel del Detalle: video actual, guardado y visto.
 */
class DetalleViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) : ViewModel() {

    private val _video = MutableStateFlow<ContenidoMasterMartini?>(null)
    val video: StateFlow<ContenidoMasterMartini?> = _video.asStateFlow()

    private val _videoIdActual = MutableStateFlow("")

    // Se recalcula si cambia el video o la lista de guardados
    val esGuardado: StateFlow<Boolean> = combine(_videoIdActual, repository.guardados) { id, guardados ->
        id.isNotEmpty() && id in guardados
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun cargarVideo(videoId: String) {
        _videoIdActual.value = videoId
        _video.value = repository.obtenerVideoPorId(videoId)
    }

    /**
     * Marca el video como visto (cuenta para el progreso del curso).
     * Se llama al reproducirlo o al abrirlo en YouTube, no al entrar al detalle.
     */
    fun marcarComoVisto() {
        val id = _videoIdActual.value
        if (id.isNotEmpty()) {
            repository.marcarComoVisto(id)
        }
    }

    fun toggleGuardado() {
        val id = _videoIdActual.value
        if (id.isNotEmpty()) {
            repository.toggleGuardado(id)
        }
    }

    fun obtenerCursoAsociado(): Curso? {
        val id = _videoIdActual.value
        return if (id.isNotEmpty()) repository.obtenerCursoPorVideoId(id) else null
    }
}
