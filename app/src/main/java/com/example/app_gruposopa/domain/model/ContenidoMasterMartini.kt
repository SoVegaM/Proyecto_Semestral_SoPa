package com.example.app_gruposopa.domain.model

/**
 *tipo de contenido ofrecido
 */
enum class TipoContenido {
    RECETA,
    CURSO_ESPECIALIDAD,
    VIDEO_TECNICO
}

/**
 * Modelo de datos para cursos, recetas y contenidos.
 */
data class ContenidoMasterMartini(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val descripcion: String,
    val duracionOPreparacion: String,
    val tipo: TipoContenido,
    val insumoDestacado: String
)
