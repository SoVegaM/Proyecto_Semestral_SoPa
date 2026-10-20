package com.example.app_gruposopa.navigation

/**
 * Eventos de navegación para que un ViewModel pida navegar sin usar el NavController.
 * Todavía no se usan.
 */
sealed class NavigationEvent {
    data class NavigateTo(val route: String) : NavigationEvent()
    object PopBackStack : NavigationEvent()
    data class PopUpTo(val route: String, val inclusive: Boolean = false) : NavigationEvent()
}
