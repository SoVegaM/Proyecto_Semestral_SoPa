package com.example.app_gruposopa.domain.model

/**
 * Tipo de contenido para el filtro de Vista 10 · Mis guardados.
 * RECETA: preparación paso a paso. VIDEO: técnica, tips o usos de un insumo.
 */
enum class TipoContenido(val etiqueta: String) {
    RECETA("Receta"),
    VIDEO("Video")
}

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
    val youtubeVideoId: String,
    val tipo: TipoContenido = TipoContenido.RECETA
) {
    /**
     * URL oficial de la miniatura de YouTube en alta calidad.
     */
    val miniaturaUrl: String
        get() = "https://img.youtube.com/vi/$youtubeVideoId/hqdefault.jpg"
}
