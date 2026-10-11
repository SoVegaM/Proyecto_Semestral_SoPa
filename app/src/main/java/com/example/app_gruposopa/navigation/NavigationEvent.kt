package com.example.app_gruposopa.navigation

/**
 * Eventos de navegación desacoplados según la arquitectura de Compose.
 * Permite que los ViewModels o componentes emitan eventos de navegación
 * sin depender directamente del NavController.
 */
sealed class NavigationEvent {
    data class NavigateTo(val route: String) : NavigationEvent()
    object PopBackStack : NavigationEvent()
    data class PopUpTo(val route: String, val inclusive: Boolean = false) : NavigationEvent()
}
