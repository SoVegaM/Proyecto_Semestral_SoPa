package com.example.app_gruposopa.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_gruposopa.model.ContenidoMasterMartini
import com.example.app_gruposopa.repository.ContenidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel que gestiona el estado de presentación para la pantalla principal (HomeScreen).
 * Permite listar los contenidos, filtrar por categorías y consultar las métricas clave.
 * Cumple con el patrón arquitectónico MVVM solicitado en las Guías 7 y 9.
 */
class HomeViewModel(
    private val repository: ContenidoRepository = ContenidoRepository()
) : ViewModel() {

    private val _todosLosContenidos = repository.obtenerContenidos()

    private val _categoriaSeleccionada = MutableStateFlow("Todas")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _contenidosFiltrados = MutableStateFlow(_todosLosContenidos)
    val contenidosFiltrados: StateFlow<List<ContenidoMasterMartini>> = _contenidosFiltrados.asStateFlow()

    val estadisticas: List<Pair<String, String>> = listOf(
        "Especialidades" to "5",
        "Capacitaciones" to "50+",
        "Certificación" to "Escuela Unica"
    )

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
        if (categoria == "Todas") {
            _contenidosFiltrados.value = _todosLosContenidos
        } else {
            _contenidosFiltrados.value = _todosLosContenidos.filter { it.categoria == categoria }
        }
    }
}
