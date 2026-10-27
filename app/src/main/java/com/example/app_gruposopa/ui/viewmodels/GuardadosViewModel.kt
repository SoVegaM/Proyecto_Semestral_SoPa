package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.model.TipoContenido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// Filtros de Mis guardados. Todos va con tipo null
enum class FiltroGuardados(val etiqueta: String, val tipo: TipoContenido?) {
    TODOS("Todos", null),
    RECETAS("Recetas", TipoContenido.RECETA),
    TECNICAS("Técnicas", TipoContenido.TECNICA)
}

class GuardadosViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) : ViewModel() {

    private val _filtro = MutableStateFlow(FiltroGuardados.TODOS)
    val filtro: StateFlow<FiltroGuardados> = _filtro.asStateFlow()

    // Lista completa sin filtro, para el mensaje cuando no hay nada
    val guardados: StateFlow<List<ContenidoMasterMartini>> = repository.guardados
        .map { ids -> repository.obtenerVideos().filter { it.id in ids } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val guardadosFiltrados: StateFlow<List<ContenidoMasterMartini>> =
        combine(guardados, _filtro) { lista, filtro ->
            if (filtro.tipo == null) lista else lista.filter { it.tipo == filtro.tipo }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seleccionarFiltro(filtro: FiltroGuardados) {
        _filtro.value = filtro
    }

    fun quitarGuardado(videoId: String) {
        if (repository.esGuardado(videoId)) {
            repository.toggleGuardado(videoId)
        }
    }
}
