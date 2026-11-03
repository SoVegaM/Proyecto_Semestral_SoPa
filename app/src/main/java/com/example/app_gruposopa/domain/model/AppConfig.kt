package com.example.app_gruposopa.domain.model

// Valores fijos de la app
data class AppConfig(
    // Tamaño que corresponde a la escala normal en el ajuste de Accesibilidad
    val tamanoTextoBase: Float = 16f,
    val largoMaximoNota: Int = 200,
    val sitioWeb: String = "https://mastermartini.cl",
    val videoIdPorDefecto: String = "v01",
    val nombreInvitado: String = "Invitado",
    val detalleNegocioVacio: String = "Sin datos del negocio",
    // Nombre con el que el JS del reproductor llama a Kotlin
    val nombrePuenteReproductor: String = "PuenteReproductor",
    val nombreBaseDatos: String = "master_martini.db",
    val nombreDataStore: String = "settings_preferences"
)

val Configuracion = AppConfig()