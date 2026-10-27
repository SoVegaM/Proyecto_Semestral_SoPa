package com.example.app_gruposopa.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app_gruposopa.ui.screens.BienvenidaScreen
import com.example.app_gruposopa.ui.screens.CursosScreen
import com.example.app_gruposopa.ui.screens.DetalleScreen
import com.example.app_gruposopa.ui.screens.GuardadosScreen
import com.example.app_gruposopa.ui.screens.HomeScreen
import com.example.app_gruposopa.ui.screens.PlaceholderVistaScreen
import com.example.app_gruposopa.ui.viewmodels.MainViewModel

/**
 * Navegación de la app. Primer uso: Bienvenida → Ingreso → Perfil de negocio → Inicio.
 * Las pantallas navegan a través del MainViewModel.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
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
                    mainViewModel.navigateTo(AppScreen.Ingreso.route)
                }
            )
        }

        // Vista 2: Ingreso y registro
        composable(route = AppScreen.Ingreso.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Ingreso,
                onIrAInicio = mainViewModel::entrarAInicio
            )
        }

        // Vista 3: Perfil de negocio
        composable(route = AppScreen.PerfilNegocio.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.PerfilNegocio,
                onIrAInicio = mainViewModel::entrarAInicio
            )
        }

        // Vista 4: Inicio (Centro técnico)
        composable(route = AppScreen.Inicio.route) {
            HomeScreen(
                onContenidoClick = { video ->
                    mainViewModel.irADetalle(video.id)
                },
                onNavigateTab = { ruta -> mainViewModel.irAPestana(ruta) },
                onGuardadosClick = { mainViewModel.navigateTo(AppScreen.Guardados.route) }
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
                onVolverClick = { mainViewModel.navigateBack() },
                onConsultarClick = { video ->
                    Toast.makeText(
                        context,
                        "Consultando sobre ${video?.producto ?: "insumo"}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onVerCursoClick = {
                    mainViewModel.irAPestana(AppScreen.Cursos.route)
                }
            )
        }

        // Vista 6: Cursos (Escuela Única)
        composable(route = AppScreen.Cursos.route) {
            CursosScreen(
                onModuloClick = { videoId ->
                    mainViewModel.irADetalle(videoId)
                },
                onNavigateTab = { ruta -> mainViewModel.irAPestana(ruta) }
            )
        }

        // Vista 7: Eventos y contacto
        composable(route = AppScreen.Eventos.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Eventos,
                onIrAInicio = mainViewModel::entrarAInicio
            )
        }

        // Vista 8: Perfil
        composable(route = AppScreen.Perfil.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Perfil,
                onIrAInicio = mainViewModel::entrarAInicio
            )
        }

        // Vista 9: Accesibilidad
        composable(route = AppScreen.Accesibilidad.route) {
            PlaceholderVistaScreen(
                pantallaActual = AppScreen.Accesibilidad,
                onIrAInicio = mainViewModel::entrarAInicio
            )
        }

        // Vista 10: Mis guardados
        composable(route = AppScreen.Guardados.route) {
            GuardadosScreen(
                onVolverClick = { mainViewModel.navigateBack() },
                onVideoClick = { videoId ->
                    mainViewModel.irADetalle(videoId)
                }
            )
        }

    }
}
