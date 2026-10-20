package com.example.app_gruposopa.navigation

/**
 * Constantes y definición centralizada de rutas de la aplicación Master Martini.
 * Corresponde a AppRoutes.kt según la estructura de arquitectura modular.
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
