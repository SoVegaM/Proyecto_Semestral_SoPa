package com.example.app_gruposopa.domain.model

/**
 * Receta (una preparación) o técnica (cómo usar un insumo). Se muestra en las tarjetas y en Mis guardados.
 */
enum class TipoContenido(val etiqueta: String) {
    RECETA("Receta"),
    TECNICA("Técnica")
}

/**
 * Un video de Master Martini (se muestra en Inicio, Detalle y Cursos).
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
     * URL oficial de la miniatura de YouTube
     */
    val miniaturaUrl: String
        get() = "https://img.youtube.com/vi/$youtubeVideoId/hqdefault.jpg"
}
