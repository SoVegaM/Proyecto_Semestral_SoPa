package com.example.app_gruposopa.domain.model

// Opciones de la pantalla Perfil de negocio
data class OpcionesNegocio(
    val tipos: List<String> = listOf("Panadería", "Pastelería", "Chocolatería", "Restaurante"),
    val tamanos: List<String> = listOf("Pequeño", "Mediano", "Grande"),
    val intereses: List<String> = listOf("Recetas", "Videos", "Cursos", "Eventos")
)

val OpcionesDeNegocio = OpcionesNegocio()