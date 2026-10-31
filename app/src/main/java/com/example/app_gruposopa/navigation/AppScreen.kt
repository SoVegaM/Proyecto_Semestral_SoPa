package com.example.app_gruposopa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Las 10 pantallas con su ruta. Las que tienen titulo e icono van en la barra de abajo.
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
        route = AppRoutes.BIENVENIDA,
        nombre = "Vista 1 - Bienvenida",
        descripcion = "Entrada con marca, lema 'Qualità italiana' y botón Comenzar"
    )

    // 2. Ingreso y registro (Login o invitado)
    object Ingreso : AppScreen(
        route = AppRoutes.INGRESO,
        nombre = "Vista 2 - Ingreso y registro",
        descripcion = "Login simulado, creación de cuenta y opción Continuar como invitado"
    )

    // 3. Perfil de negocio (Segmentación)
    object PerfilNegocio : AppScreen(
        route = AppRoutes.PERFIL_NEGOCIO,
        nombre = "Vista 3 - Perfil de negocio",
        descripcion = "Tipo y tamaño de negocio, intereses gastronómicos y continuar"
    )

    // 4. Inicio (Centro técnico)
    object Inicio : AppScreen(
        route = AppRoutes.INICIO,
        nombre = "Vista 4 - Inicio (Centro técnico)",
        descripcion = "Centro técnico gastronómico, buscador, catálogo y filtros",
        titulo = "Inicio",
        icono = Icons.Default.Home
    )

    // 5. Detalle de contenido
    object Detalle : AppScreen(
        route = AppRoutes.DETALLE,
        nombre = "Vista 5 - Detalle de contenido",
        descripcion = "Reproductor de video, insumo destacado, botón guardar y consultar"
    ) {
        fun crearRuta(videoId: String): String = AppRoutes.detalle(videoId)
    }

    // 6. Cursos (Escuela Única)
    object Cursos : AppScreen(
        route = AppRoutes.CURSOS,
        nombre = "Vista 6 - Cursos (Escuela Única)",
        descripcion = "Lista de cursos con cantidad de módulos, estado y barra de progreso",
        titulo = "Cursos",
        icono = Icons.Default.School
    )

    // 7. Eventos y contacto
    object Eventos : AppScreen(
        route = AppRoutes.EVENTOS,
        nombre = "Vista 7 - Eventos y contacto",
        descripcion = "Talleres, demostraciones, inscripción, llamada y asesoría WhatsApp",
        titulo = "Eventos",
        icono = Icons.Default.CalendarMonth
    )

    // 8. Perfil
    object Perfil : AppScreen(
        route = AppRoutes.PERFIL,
        nombre = "Vista 8 - Perfil",
        descripcion = "Datos del usuario, accesos a guardados, accesibilidad y cerrar sesión",
        titulo = "Perfil",
        icono = Icons.Default.Person
    )

    // 9. Accesibilidad
    object Accesibilidad : AppScreen(
        route = AppRoutes.ACCESIBILIDAD,
        nombre = "Vista 9 - Accesibilidad",
        descripcion = "Tamaño de texto, alto contraste, texto en negrita y vista previa"
    )

    // 10. Mis guardados
    object Guardados : AppScreen(
        route = AppRoutes.GUARDADOS,
        nombre = "Vista 10 - Mis guardados",
        descripcion = "Favoritos guardados, filtro Todos / Recetas / Técnicas"
    )

    // Desde el menú lateral
    object Sobre : AppScreen(
        route = AppRoutes.SOBRE,
        nombre = "Sobre Master Martini",
        descripcion = "Quiénes son Master Martini y Unigrà, y enlace a su sitio web"
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
                Guardados,
                Sobre
            )

        // Pestañas de la barra inferior
        val vistasBarraInferior: List<AppScreen>
            get() = listOf(
                Inicio,
                Cursos,
                Eventos,
                Perfil
            )
    }
}
