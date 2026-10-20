package com.example.app_gruposopa.domain.model

/**
 * Curso de Escuela Única. Solo guarda los ids de sus videos.
 */
data class Curso(
    val id: String,
    val titulo: String,
    val categoria: String,
    val descripcion: String,
    val videoIds: List<String>
)
