package com.example.app_gruposopa.domain.model

// Categorías de los videos y del filtro de Inicio
data class AppCategorias(
    val cremas: String = "Cremas Vegetales UHT",
    val chocolates: String = "Chocolates & Sucedáneos",
    val gelateria: String = "Gelatería",
    val panaderia: String = "Panadería & Pastelería",

    // Opción del filtro que no filtra nada

    val todas: String = "Todas"
)

val Categorias = AppCategorias()