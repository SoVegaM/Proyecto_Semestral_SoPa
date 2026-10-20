package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.model.Curso
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Datos de un curso listos para mostrar.
 */
data class CursoUIState(
    val curso: Curso,
    val modulosTotales: Int,
    val modulosCompletados: Int,
    val progreso: Float,
    val estadoEtiqueta: String, // "Sin iniciar", "En curso", "Completado"
    val modulos: List<ContenidoMasterMartini>,
    val estaExpandido: Boolean
)

/**
 * ViewModel de Cursos: lista de cursos, videos vistos y cursos abiertos.
 */
class CursosViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) : ViewModel() {

    val cursos = repository.obtenerCursos()
    val videosVistos: StateFlow<Set<String>> = repository.videosVistos

    private val _cursosExpandidos = MutableStateFlow<Set<String>>(emptySet())
    val cursosExpandidos: StateFlow<Set<String>> = _cursosExpandidos.asStateFlow()

    fun toggleExpandido(cursoId: String) {
        _cursosExpandidos.update { actual ->
            if (actual.contains(cursoId)) actual - cursoId else actual + cursoId
        }
    }

    fun obtenerModulos(curso: Curso): List<ContenidoMasterMartini> {
        return repository.obtenerVideosPorIds(curso.videoIds)
    }

    fun esModuloVisto(videoId: String): Boolean {
        return repository.esVisto(videoId)
    }
}
