package com.example.app_gruposopa.domain.model

/**
 * Modelo de datos puro para los Cursos de Escuela Única.
 * Referencia videos por su identificador (videoIds) sin duplicar datos.
 */
data class Curso(
    val id: String,
    val titulo: String,
    val categoria: String,
    val descripcion: String,
    val videoIds: List<String>
)
