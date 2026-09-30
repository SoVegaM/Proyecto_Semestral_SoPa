package com.example.app_gruposopa.model

/**
 * Representa el tipo de contenido técnico ofrecido por Master Martini.
 */
enum class TipoContenido {
    RECETA,
    CURSO_ESPECIALIDAD,
    VIDEO_TECNICO
}

/**
 * Modelo de datos para cursos, recetas y contenidos técnicos de Master Martini.
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
