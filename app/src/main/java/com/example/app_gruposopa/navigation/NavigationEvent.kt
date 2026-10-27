package com.example.app_gruposopa.navigation

/**
 * Eventos de navegación que emite el MainViewModel y que MainActivity convierte en navegación real.
 */
sealed class NavigationEvent {

    /**
     * Ir a una ruta. La ruta es un String porque Detalle lleva el id del video (detalle/{videoId}).
     * @param popUpToRoute si no es null, se borra el historial hasta esa ruta.
     * @param inclusive si es true, también se borra popUpToRoute.
     * @param singleTop evita abrir dos veces la misma pantalla seguida.
     * @param saveState guarda y recupera el estado de la pantalla (para las pestañas).
     */
    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false,
        val saveState: Boolean = false
    ) : NavigationEvent()

    // Volver a la pantalla anterior
    object PopBackStack : NavigationEvent()

    // Subir un nivel en la navegación (como la flecha de la barra superior)
    object NavigateUp : NavigationEvent()
}
