package com.example.app_gruposopa.navigation

// Eventos que manda el MainViewModel para que MainActivity navegue
sealed class NavigationEvent {

    // La ruta es String y no AppScreen porque detalle lleva el id del video
    // popUpToRoute: hasta dónde se borra el historial. saveState se usa en las pestañas
    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false,
        val saveState: Boolean = false
    ) : NavigationEvent()

    // Volver a la pantalla anterior
    object PopBackStack : NavigationEvent()

    // Subir un nivel (la flecha de la barra de arriba)
    object NavigateUp : NavigationEvent()
}
