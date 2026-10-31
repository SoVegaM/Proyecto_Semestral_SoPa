package com.example.app_gruposopa.navigation

// Rutas de la app
object AppRoutes {
    const val BIENVENIDA = "bienvenida"
    const val INGRESO = "ingreso"
    const val PERFIL_NEGOCIO = "perfil_negocio"
    const val INICIO = "inicio"
    const val CURSOS = "cursos"
    const val EVENTOS = "eventos"
    const val PERFIL = "perfil"
    const val ACCESIBILIDAD = "accesibilidad"
    const val GUARDADOS = "guardados"
    const val SOBRE = "sobre_master_martini"

    // Detalle lleva el id del video, ej: detalle/v01
    const val ARG_VIDEO_ID = "videoId"
    const val DETALLE = "detalle/{$ARG_VIDEO_ID}"

    fun detalle(videoId: String) = "detalle/$videoId"
}
