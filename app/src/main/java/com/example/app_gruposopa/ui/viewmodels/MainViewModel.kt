package com.example.app_gruposopa.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Centraliza la navegación: las pantallas le piden ir a algún lado y este ViewModel
 * emite el evento. MainActivity lo escucha y usa el NavController.
 */
class MainViewModel : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

    // Curso que Cursos debe abrir al llegar (desde "Ver curso" en Detalle)
    private val _cursoPorAbrir = MutableStateFlow<String?>(null)
    val cursoPorAbrir: StateFlow<String?> = _cursoPorAbrir.asStateFlow()

    fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        singleTop: Boolean = false,
        saveState: Boolean = false
    ) {
        viewModelScope.launch {
            _navigationEvents.emit(
                NavigationEvent.NavigateTo(route, popUpToRoute, inclusive, singleTop, saveState)
            )
        }
    }

    // Volver atrás
    fun navigateBack() {
        viewModelScope.launch { _navigationEvents.emit(NavigationEvent.PopBackStack) }
    }

    // Subir un nivel (padre)
    fun navigateUp() {
        viewModelScope.launch { _navigationEvents.emit(NavigationEvent.NavigateUp) }
    }

    // Cambia de pestaña sin apilar pantallas repetidas; Inicio queda al fondo del historial
    fun irAPestana(ruta: String) {
        navigateTo(ruta, popUpToRoute = AppScreen.Inicio.route, singleTop = true, saveState = true)
    }

    // Entra a Inicio y borra Bienvenida e Ingreso del historial
    fun entrarAInicio() {
        navigateTo(AppScreen.Inicio.route, popUpToRoute = AppScreen.Bienvenida.route, inclusive = true, singleTop = true)
    }

    fun verCurso(cursoId: String) {
        _cursoPorAbrir.value = cursoId
        irAPestana(AppScreen.Cursos.route)
    }

    fun cursoAbierto() {
        _cursoPorAbrir.value = null
    }

    fun irADetalle(videoId: String) {
        navigateTo(AppScreen.Detalle.crearRuta(videoId))
    }
}
