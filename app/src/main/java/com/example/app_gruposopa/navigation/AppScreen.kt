package com.example.app_gruposopa.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector

// Las pantallas con su ruta. Las que tienen titulo e icono van en la barra de abajo
data class AppScreen(
    val route: String,
    val nombre: String,
    val descripcion: String,
    val titulo: String = "",              // texto corto, solo para las 4 pestañas
    val icono: ImageVector? = null        // solo para las 4 pestañas
) {
    // Ruta concreta del detalle de un video (se usa con AppScreen.Detalle)
    fun crearRuta(videoId: String): String = Rutas.detalle(videoId)

    companion object {
        val Bienvenida = AppScreen(
            route = Rutas.bienvenida,
            nombre = "Vista 1 - Bienvenida",
            descripcion = "Entrada con marca, lema 'Qualità italiana' y botón Comenzar"
        )

        val Ingreso = AppScreen(
            route = Rutas.ingreso,
            nombre = "Vista 2 - Ingreso y registro",
            descripcion = "Login simulado, creación de cuenta y opción Continuar como invitado"
        )

        val PerfilNegocio = AppScreen(
            route = Rutas.perfilNegocio,
            nombre = "Vista 3 - Perfil de negocio",
            descripcion = "Tipo y tamaño de negocio, intereses gastronómicos y continuar"
        )

        val Inicio = AppScreen(
            route = Rutas.inicio,
            nombre = "Vista 4 - Inicio (Centro técnico)",
            descripcion = "Centro técnico gastronómico, buscador, catálogo y filtros",
            titulo = "Inicio",
            icono = Icons.Default.Home
        )

        val Detalle = AppScreen(
            route = Rutas.detalle,
            nombre = "Vista 5 - Detalle de contenido",
            descripcion = "Reproductor de video, insumo destacado, botón guardar y consultar"
        )

        val Cursos = AppScreen(
            route = Rutas.cursos,
            nombre = "Vista 6 - Cursos (Escuela Única)",
            descripcion = "Lista de cursos con cantidad de módulos, estado y barra de progreso",
            titulo = "Cursos",
            icono = Icons.Default.School
        )

        val Eventos = AppScreen(
            route = Rutas.eventos,
            nombre = "Vista 7 - Eventos y contacto",
            descripcion = "Talleres, demostraciones, inscripción, llamada y asesoría WhatsApp",
            titulo = "Eventos",
            icono = Icons.Default.CalendarMonth
        )

        val Perfil = AppScreen(
            route = Rutas.perfil,
            nombre = "Vista 8 - Perfil",
            descripcion = "Datos del usuario, accesos a guardados, accesibilidad y cerrar sesión",
            titulo = "Perfil",
            icono = Icons.Default.Person
        )

        val Accesibilidad = AppScreen(
            route = Rutas.accesibilidad,
            nombre = "Vista 9 - Accesibilidad",
            descripcion = "Tamaño de texto, alto contraste, texto en negrita y vista previa"
        )

        val Guardados = AppScreen(
            route = Rutas.guardados,
            nombre = "Vista 10 - Mis guardados",
            descripcion = "Favoritos guardados, filtro Todos / Recetas / Técnicas"
        )

        val Sobre = AppScreen(
            route = Rutas.sobre,
            nombre = "Sobre Master Martini",
            descripcion = "Quiénes son Master Martini y Unigrà, y enlace a su sitio web"
        )

        // Tienen que ir después de las pantallas, porque se arman con ellas
        val todasLasVistas: List<AppScreen> = listOf(
            Bienvenida, Ingreso, PerfilNegocio, Inicio, Detalle,
            Cursos, Eventos, Perfil, Accesibilidad, Guardados, Sobre
        )

        // Pestañas de la barra inferior
        val vistasBarraInferior: List<AppScreen> = listOf(Inicio, Cursos, Eventos, Perfil)
    }
}