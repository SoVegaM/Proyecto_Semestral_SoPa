package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel para la Vista 5 · Detalle de contenido.
 * Carga el video por ID, lo marca como visto al reproducirse y alterna guardado.
 */
class DetalleViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) : ViewModel() {

    private val _video = MutableStateFlow<ContenidoMasterMartini?>(null)
    val video: StateFlow<ContenidoMasterMartini?> = _video.asStateFlow()

    private val _videoIdActual = MutableStateFlow("")

    val esGuardado: StateFlow<Boolean> = repository.guardados
        .map { guardados -> _videoIdActual.value.isNotEmpty() && guardados.contains(_videoIdActual.value) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun cargarVideo(videoId: String) {
        _videoIdActual.value = videoId
        _video.value = repository.obtenerVideoPorId(videoId)
    }

    /**
     * Cuenta el módulo como visto para el progreso de Cursos.
     * Se llama cuando el video empieza a reproducirse (o se abre en YouTube), no al abrir el detalle.
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

    fun obtenerCursoAsociado(): com.example.app_gruposopa.domain.model.Curso? {
        val id = _videoIdActual.value
        return if (id.isNotEmpty()) repository.obtenerCursoPorVideoId(id) else null
    }
}
