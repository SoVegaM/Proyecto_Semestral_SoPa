package com.example.app_gruposopa.domain.model

/**
 * Modelo de datos puro para los videos y contenidos técnicos de Master Martini.
 * Conecta las vistas Inicio, Detalle y Cursos con los datos reales de YouTube.
 */
data class ContenidoMasterMartini(
    val id: String,
    val titulo: String,
    val descripcion: String, // Siempre "Video con {producto}"
    val duracion: String,
    val categoria: String,
    val producto: String,
    val youtubeVideoId: String
) {
    /**
     * URL oficial de la miniatura de YouTube en alta calidad.
     */
    val miniaturaUrl: String
        get() = "https://img.youtube.com/vi/$youtubeVideoId/hqdefault.jpg"
}
