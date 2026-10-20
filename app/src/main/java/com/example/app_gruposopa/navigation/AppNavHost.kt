package com.example.app_gruposopa.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_gruposopa.ui.screens.BienvenidaScreen
import com.example.app_gruposopa.ui.screens.CursosScreen
import com.example.app_gruposopa.ui.screens.DetalleScreen
import com.example.app_gruposopa.ui.screens.GuardadosScreen
import com.example.app_gruposopa.ui.screens.HomeScreen
import com.example.app_gruposopa.ui.screens.PlaceholderVistaScreen

/**
 * NavHost principal con las 10 rutas vacías de la aplicación Master Martini.
 * Fase 0 - Base Común del MVP Semestral (DSY1105).
 *
 * Flujo de navegación:
 * 1. Bienvenida (Entrada con marca)
 * 2. Ingreso y registro (Login o invitado)
 * 3. Perfil de negocio (Segmentación)
 * 4. Inicio (Centro técnico)
 * 5. Detalle de contenido (Video e insumo)
 * 6. Cursos (Escuela Única)
 * 7. Eventos y contacto (Asesoría)
 * 8. Perfil (Mis datos)
 * 9. Accesibilidad (Texto, contraste)
 * 10. Mis guardados (Favoritos)
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppScreen.Bienvenida.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Vista 1: Bienvenida
        composable(route = AppScreen.Bienvenida.route) {
            BienvenidaScreen(
                onComenzarClick = {
                    navController.navigate(AppScreen.Ingreso.route)
                }
            )
        }

        // Vista 2: Ingreso y registro
        composable(route = AppScreen.Ingreso.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Ingreso,
                navController = navController
            )
        }

        // Vista 3: Perfil de negocio
        composable(route = AppScreen.PerfilNegocio.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.PerfilNegocio,
                navController = navController
            )
        }

        // Vista 4: Inicio (Centro técnico)
        composable(route = AppScreen.Inicio.route) {
            HomeScreen(
                onContenidoClick = { video ->
                    navController.navigate(AppScreen.Detalle.crearRuta(video.id))
                },
                onNavigateTab = { ruta -> navController.navegarAPestana(ruta) },
                onGuardadosClick = { navController.navigate(AppScreen.Guardados.route) }
            )
        }

        // Vista 5: Detalle de contenido
        composable(
            route = AppScreen.Detalle.route,
            arguments = listOf(
                navArgument("videoId") {
                    type = NavType.StringType
                    defaultValue = "v01"
                }
            )
        ) { backStackEntry ->
            val context = LocalContext.current
            val videoId = backStackEntry.arguments?.getString("videoId") ?: "v01"
            DetalleScreen(
                videoId = videoId,
                onVolverClick = { navController.popBackStack() },
                onConsultarClick = { video ->
                    Toast.makeText(
                        context,
                        "Consultando sobre ${video?.producto ?: "insumo"}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onVerCursoClick = {
                    navController.navegarAPestana(AppScreen.Cursos.route)
                }
            )
        }

        // Vista 6: Cursos (Escuela Única)
        composable(route = AppScreen.Cursos.route) {
            CursosScreen(
                onModuloClick = { videoId ->
                    navController.navigate(AppScreen.Detalle.crearRuta(videoId))
                },
                onNavigateTab = { ruta -> navController.navegarAPestana(ruta) }
            )
        }

        // Vista 7: Eventos y contacto
        composable(route = AppScreen.Eventos.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Eventos,
                navController = navController
            )
        }

        // Vista 8: Perfil
        composable(route = AppScreen.Perfil.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Perfil,
                navController = navController
            )
        }

        // Vista 9: Accesibilidad
        composable(route = AppScreen.Accesibilidad.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Accesibilidad,
                navController = navController
            )
        }

        // Vista 10: Mis guardados
        composable(route = AppScreen.Guardados.route) {
            GuardadosScreen(
                onVolverClick = { navController.popBackStack() },
                onVideoClick = { videoId ->
                    navController.navigate(AppScreen.Detalle.crearRuta(videoId))
                }
            )
        }

    }
}

/**
 * Navegación entre las pestañas de la barra inferior.
 * Deja siempre Inicio como base del historial y no apila pestañas repetidas:
 * Atrás desde cualquier pestaña vuelve a Inicio, y desde Inicio sale de la app.
 */
fun NavHostController.navegarAPestana(ruta: String) {
    navigate(ruta) {
        popUpTo(AppScreen.Inicio.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

/**
 * Entrada a Inicio después del primer uso (Bienvenida → Ingreso → Perfil de negocio).
 * Limpia esas pantallas del historial para que Atrás no vuelva a ellas.
 */
fun NavController.entrarAInicio() {
    navigate(AppScreen.Inicio.route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
}
