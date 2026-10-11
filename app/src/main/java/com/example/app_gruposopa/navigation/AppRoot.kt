package com.example.app_gruposopa.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_gruposopa.ui.screens.CursosScreen
import com.example.app_gruposopa.ui.screens.DetalleScreen
import com.example.app_gruposopa.ui.screens.HomeScreen
import com.example.app_gruposopa.ui.screens.PlaceholderVistaScreen

class AppRoot {
    @Composable
    fun AppRoot() {
        val context = LocalContext.current
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val rutaActual = backStackEntry?.destination?.route
        val mostrarBarra = AppScreen.vistasBarraInferior.any { it.route == rutaActual }

        Scaffold(
            bottomBar = {
                if (mostrarBarra) {
                    NavigationBar {
                        AppScreen.vistasBarraInferior.forEach { screen ->
                            NavigationBarItem(
                                selected = rutaActual == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(AppScreen.Inicio.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { screen.icono?.let { Icon(it, contentDescription = null) } },
                                label = { Text(screen.titulo) }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppScreen.Bienvenida.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                // Vista 1: Bienvenida
                composable(AppScreen.Bienvenida.route) {
                    PlaceholderVistaScreen(AppScreen.Bienvenida, navController)
                }
                // Vista 2: Ingreso
                composable(AppScreen.Ingreso.route) {
                    PlaceholderVistaScreen(AppScreen.Ingreso, navController)
                }
                // Vista 3: Perfil Negocio
                composable(AppScreen.PerfilNegocio.route) {
                    PlaceholderVistaScreen(AppScreen.PerfilNegocio, navController)
                }
                // Vista 4: Inicio (Centro técnico)
                composable(AppScreen.Inicio.route) {
                    HomeScreen(
                        onContenidoClick = { video ->
                            navController.navigate(AppScreen.Detalle.crearRuta(video.id))
                        },
                        onNavigateTab = { ruta ->
                            navController.navigate(ruta)
                        }
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
                ) { entry ->
                    val videoId = entry.arguments?.getString("videoId") ?: "v01"
                    DetalleScreen(
                        videoId = videoId,
                        onVolverClick = { navController.popBackStack() },
                        onConsultarClick = { video ->
                            Toast.makeText(
                                context,
                                "Consultando sobre ${video?.producto ?: "insumo"}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
                // Vista 6: Cursos (Escuela Única)
                composable(AppScreen.Cursos.route) {
                    CursosScreen(
                        onModuloClick = { videoId ->
                            navController.navigate(AppScreen.Detalle.crearRuta(videoId))
                        },
                        onNavigateTab = { ruta ->
                            navController.navigate(ruta)
                        }
                    )
                }
                // Vista 7: Eventos
                composable(AppScreen.Eventos.route) {
                    PlaceholderVistaScreen(AppScreen.Eventos, navController)
                }
                // Vista 8: Perfil
                composable(AppScreen.Perfil.route) {
                    PlaceholderVistaScreen(AppScreen.Perfil, navController)
                }
                // Vista 9: Accesibilidad
                composable(AppScreen.Accesibilidad.route) {
                    PlaceholderVistaScreen(AppScreen.Accesibilidad, navController)
                }
                // Vista 10: Guardados
                composable(AppScreen.Guardados.route) {
                    PlaceholderVistaScreen(AppScreen.Guardados, navController)
                }
            }
        }
    }
}