package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel de Inicio: búsqueda y filtro por categoría.
 */
class HomeViewModel(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) : ViewModel() {

    private val _todosLosVideos = repository.obtenerVideos()

    private val _categoriaSeleccionada = MutableStateFlow("Todas")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    private val _contenidosFiltrados = MutableStateFlow(_todosLosVideos)
    val contenidosFiltrados: StateFlow<List<ContenidoMasterMartini>> = _contenidosFiltrados.asStateFlow()

    // "Todas" + las categorías que aparecen en los videos
    val categorias: List<String> = listOf("Todas") + _todosLosVideos.map { it.categoria }.distinct()

    fun seleccionarCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
        aplicarFiltros()
    }

    fun onTextoBusquedaChange(nuevoTexto: String) {
        _textoBusqueda.value = nuevoTexto
        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        val cat = _categoriaSeleccionada.value
        val busqueda = _textoBusqueda.value.trim().lowercase()

        _contenidosFiltrados.value = _todosLosVideos.filter { video ->
            val coincideCategoria = (cat == "Todas" || video.categoria == cat)
            val coincideBusqueda = busqueda.isEmpty() ||
                    video.titulo.lowercase().contains(busqueda) ||
                    video.producto.lowercase().contains(busqueda)
            coincideCategoria && coincideBusqueda
        }
    }
}
