package com.example.app_gruposopa.domain.model

// Taller o demostración a la que el usuario se puede inscribir
data class Evento(
    val id: String,
    val titulo: String,
    val detalle: String
)

// Eventos disponibles
val CatalogoEventos: List<Evento> = listOf(
    Evento("taller_templado", "Taller de templado de chocolate", "15 dic - Presencial - Centro Técnico"),
    Evento("demo_heladeria", "Demo de heladería artesanal", "28 dic - Online vía Zoom")
)