package com.example.app_gruposopa.navigation

// Rutas de la app como data class. Se usa la instancia Rutas en todo el proyecto

data class AppRoutes(
    val bienvenida: String = "bienvenida",
    val ingreso: String = "ingreso",
    val perfilNegocio: String = "perfil_negocio",
    val inicio: String = "inicio",
    val cursos: String = "cursos",
    val eventos: String = "eventos",
    val perfil: String = "perfil",
    val accesibilidad: String = "accesibilidad",
    val guardados: String = "guardados",
    val sobre: String = "sobre_master_martini",
    // Detalle lleva el id del video, ej: detalle/v01
    val argVideoId: String = "videoId"
) {
    // Patrón que se registra en el NavHost: detalle/{videoId}
    val detalle: String get() = "detalle/{$argVideoId}"

    // Ruta concreta para navegar: detalle/v01
    fun detalle(videoId: String): String = "detalle/$videoId"
}

// Instancia única
val Rutas = AppRoutes()