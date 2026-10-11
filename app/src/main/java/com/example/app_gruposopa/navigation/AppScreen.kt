package com.example.app_gruposopa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Definición de las 10 rutas y pantallas de la aplicación Master Martini MVP.
 * Basado en la arquitectura de la Fase 0 (Base común):
 *
 * Persona A:
 * - Vista 1: Bienvenida
 * - Vista 4: Inicio (Centro técnico)
 * - Vista 5: Detalle de contenido
 * - Vista 6: Cursos (Escuela Única)
 * - Vista 10: Mis guardados
 *
 * Persona B:
 * - Vista 2: Ingreso y registro
 * - Vista 3: Perfil de negocio
 * - Vista 7: Eventos y contacto
 * - Vista 8: Perfil
 * - Vista 9: Accesibilidad
 */
sealed class AppScreen(
    val route: String,
    val nombre: String,
    val descripcion: String,
    val titulo: String = "",              // texto corto, solo para las 4 pestañas
    val icono: ImageVector? = null        // solo para las 4 pestañas
) {
    // 1. Bienvenida (Entrada con marca)
    object Bienvenida : AppScreen(
        route = "bienvenida",
        nombre = "Vista 1 · Bienvenida",
        descripcion = "Entrada con marca, lema 'Qualità italiana' y botón Comenzar"
    )

    // 2. Ingreso y registro (Login o invitado)
    object Ingreso : AppScreen(
        route = "ingreso",
        nombre = "Vista 2 · Ingreso y registro",
        descripcion = "Login simulado, creación de cuenta y opción Continuar como invitado"
    )

    // 3. Perfil de negocio (Segmentación)
    object PerfilNegocio : AppScreen(
        route = "perfil_negocio",
        nombre = "Vista 3 · Perfil de negocio",
        descripcion = "Tipo y tamaño de negocio, intereses gastronómicos y continuar"
    )

    // 4. Inicio (Centro técnico)
    object Inicio : AppScreen(
        route = "inicio",
        nombre = "Vista 4 · Inicio (Centro técnico)",
        descripcion = "Centro técnico gastronómico, buscador, catálogo y filtros",
        titulo = "Inicio",
        icono = Icons.Default.Home
    )

    // 5. Detalle de contenido
    object Detalle : AppScreen(
        route = "detalle/{videoId}",
        nombre = "Vista 5 · Detalle de contenido",
        descripcion = "Reproductor de video, insumo destacado, botón guardar y consultar"
    ) {
        fun crearRuta(videoId: String): String = "detalle/$videoId"
    }

    // 6. Cursos (Escuela Única)
    object Cursos : AppScreen(
        route = "cursos",
        nombre = "Vista 6 · Cursos (Escuela Única)",
        descripcion = "Lista de cursos con cantidad de módulos, estado y barra de progreso",
        titulo = "Cursos",
        icono = Icons.Default.School
    )

    // 7. Eventos y contacto
    object Eventos : AppScreen(
        route = "eventos",
        nombre = "Vista 7 · Eventos y contacto",
        descripcion = "Talleres, demostraciones, inscripción, llamada y asesoría WhatsApp",
        titulo = "Eventos",
        icono = Icons.Default.CalendarMonth
    )

    // 8. Perfil
    object Perfil : AppScreen(
        route = "perfil",
        nombre = "Vista 8 · Perfil",
        descripcion = "Datos del usuario, accesos a guardados, accesibilidad y cerrar sesión",
        titulo = "Perfil",
        icono = Icons.Default.Person
    )

    // 9. Accesibilidad
    object Accesibilidad : AppScreen(
        route = "accesibilidad",
        nombre = "Vista 9 · Accesibilidad",
        descripcion = "Tamaño de texto, alto contraste, texto en negrita y vista previa"
    )

    // 10. Mis guardados
    object Guardados : AppScreen(
        route = "guardados",
        nombre = "Vista 10 · Mis guardados",
        descripcion = "Favoritos guardados, filtro Todos / Recetas / Videos"
    )

    companion object {
        val todasLasVistas: List<AppScreen>
            get() = listOf(
                Bienvenida,
                Ingreso,
                PerfilNegocio,
                Inicio,
                Detalle,
                Cursos,
                Eventos,
                Perfil,
                Accesibilidad,
                Guardados
            )

        // Vistas principales que aparecen en la barra inferior (Bottom Navigation)
        val vistasBarraInferior: List<AppScreen>
            get() = listOf(
                Inicio,
                Cursos,
                Eventos,
                Perfil
            )
    }
}
