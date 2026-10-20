package com.example.app_gruposopa.navigation

/**
 * Nombres de las rutas de la app.
 */
object AppRoutes {
    const val BIENVENIDA = "bienvenida"
    const val INGRESO = "ingreso"
    const val PERFIL_NEGOCIO = "perfil_negocio"
    const val INICIO = "inicio"
    const val DETALLE = "detalle"
    const val DETALLE_ARG = "videoId"
    const val DETALLE_CON_ARG = "detalle/{videoId}"
    const val CURSOS = "cursos"
    const val EVENTOS = "eventos"
    const val PERFIL = "perfil"
    const val ACCESIBILIDAD = "accesibilidad"
    const val GUARDADOS = "guardados"

    fun rutaDetalle(videoId: String): String = "detalle/$videoId"
}
