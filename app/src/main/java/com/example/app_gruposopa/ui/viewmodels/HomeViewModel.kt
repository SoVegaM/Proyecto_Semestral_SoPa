package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.app_gruposopa.data.repository.ContenidoRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel, gestiona el estado de presentación para la Vista 4 - Inicio (Centro técnico).
 * Permite listar contenidos, filtrar por categorías y buscar por texto de forma combinada.
 */
class HomeViewModel(
    private val repository: ContenidoRepository = ContenidoRepository()
) : ViewModel() {

    private val _todosLosContenidos = repository.obtenerContenidos()

    private val _categoriaSeleccionada = MutableStateFlow("Todas")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _textoBusqueda = MutableStateFlow("")
    val textoBusqueda: StateFlow<String> = _textoBusqueda.asStateFlow()

    private val _contenidosFiltrados = MutableStateFlow(_todosLosContenidos)
    val contenidosFiltrados: StateFlow<List<ContenidoMasterMartini>> = _contenidosFiltrados.asStateFlow()

    val categorias: List<String> = listOf(
        "Todas",
        "Panadería & Pastelería",
        "Chocolates & Sucedáneos",
        "Cremas Vegetales UHT",
        "Gelatería",
        "Escuela Unica"
    )

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

        _contenidosFiltrados.value = _todosLosContenidos.filter { item ->
            val coincideCategoria = (cat == "Todas" || item.categoria == cat)
            val coincideBusqueda = busqueda.isEmpty() ||
                    item.titulo.lowercase().contains(busqueda) ||
                    item.descripcion.lowercase().contains(busqueda) ||
                    item.insumoDestacado.lowercase().contains(busqueda)
            coincideCategoria && coincideBusqueda
        }
    }
}
