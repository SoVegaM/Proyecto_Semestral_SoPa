package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.usecase.FiltrarContenidosUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance,
    private val filtrarContenidos: FiltrarContenidosUseCase = FiltrarContenidosUseCase()
) : ViewModel() {

    private val _todosLosVideos = repository.obtenerVideos()

    // Carga simulada al abrir Inicio (guía 12)
    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _cargando.value = false
        }
    }

    private val _categoriaSeleccionada = MutableStateFlow(FiltrarContenidosUseCase.TODAS)
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    private val _contenidosFiltrados = MutableStateFlow(_todosLosVideos)
    val contenidosFiltrados: StateFlow<List<ContenidoMasterMartini>> = _contenidosFiltrados.asStateFlow()

    // "Todas" + las categorías que aparecen en los videos
    val categorias: List<String> = listOf(FiltrarContenidosUseCase.TODAS) + _todosLosVideos.map { it.categoria }.distinct()

    fun seleccionarCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
        aplicarFiltros()
    }

    fun onTextoBusquedaChange(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        _contenidosFiltrados.value = filtrarContenidos(
            _todosLosVideos,
            _categoriaSeleccionada.value,
            _textoBusqueda.value
        )
    }
}
