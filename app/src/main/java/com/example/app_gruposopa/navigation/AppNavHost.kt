package com.example.app_gruposopa.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.app_gruposopa.ui.components.OpcionMenu
import com.example.app_gruposopa.ui.screens.AccesibilidadScreen
import com.example.app_gruposopa.ui.screens.BienvenidaScreen
import com.example.app_gruposopa.ui.screens.CursosScreen
import com.example.app_gruposopa.ui.screens.DetalleScreen
import com.example.app_gruposopa.ui.screens.EventosScreen
import com.example.app_gruposopa.ui.screens.GuardadosScreen
import com.example.app_gruposopa.ui.screens.HomeScreen
import com.example.app_gruposopa.ui.screens.IngresoScreen
import com.example.app_gruposopa.ui.screens.PerfilNegocioScreen
import com.example.app_gruposopa.ui.screens.PerfilScreen
import com.example.app_gruposopa.ui.screens.SobreMasterMartiniScreen
import com.example.app_gruposopa.ui.viewmodels.MainViewModel

/**
 * Rutas de la app. La primera vez: Bienvenida → Ingreso → Perfil de negocio → Inicio.
 * Para navegar se usa el MainViewModel.
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

        // Vista 2: Ingreso y registro (Guarda el nombre ingresado)
        composable(route = AppScreen.Ingreso.route) {
            IngresoScreen(
                onIngresoExitoso = { nombreIngresado ->
                    if (nombreIngresado.isNotBlank()) {
                        mainViewModel.guardarNombreUsuario(nombreIngresado)
                    }
                    mainViewModel.navigateTo(AppScreen.PerfilNegocio.route)
                }
            )
        }

        // Vista 3: Perfil de negocio
        composable(route = AppScreen.PerfilNegocio.route) {
            PerfilNegocioScreen(
                onGuardarYContinuar = {
                    mainViewModel.entrarAInicio()
                }
            )
        }

        // Vista 4: Inicio (Centro técnico)
        composable(route = AppScreen.Inicio.route) {
            HomeScreen(
                onContenidoClick = { video ->
                    mainViewModel.irADetalle(video.id)
                },
                onNavigateTab = { ruta -> mainViewModel.irAPestana(ruta) },
                onGuardadosClick = { mainViewModel.navigateTo(AppScreen.Guardados.route) },
                onOpcionMenu = { opcion, ultimoVisto ->
                    when (opcion) {
                        OpcionMenu.GUARDADOS -> mainViewModel.navigateTo(AppScreen.Guardados.route)
                        OpcionMenu.SEGUIR_VIENDO -> ultimoVisto?.let { mainViewModel.irADetalle(it.id) }
                        OpcionMenu.ACCESIBILIDAD -> mainViewModel.navigateTo(AppScreen.Accesibilidad.route)
                        OpcionMenu.ASESOR -> mainViewModel.irAPestana(AppScreen.Eventos.route)
                        OpcionMenu.SOBRE -> mainViewModel.navigateTo(AppScreen.Sobre.route)
                        OpcionMenu.CERRAR_SESION -> mainViewModel.cerrarSesion()
                    }
                }
            )
        }

        // Vista 5: Detalle de contenido
        composable(
            route = AppScreen.Detalle.route,
            arguments = listOf(
                navArgument(AppRoutes.ARG_VIDEO_ID) {
                    type = NavType.StringType
                    defaultValue = "v01"
                }
            )
        ) { backStackEntry ->
            val context = LocalContext.current
            val videoId = backStackEntry.arguments?.getString(AppRoutes.ARG_VIDEO_ID) ?: "v01"
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
                onVerCursoClick = { cursoId -> mainViewModel.verCurso(cursoId) }
            )
        }

        // Vista 6: Cursos (Escuela Única)
        composable(route = AppScreen.Cursos.route) {
            val cursoPorAbrir by mainViewModel.cursoPorAbrir.collectAsState()
            CursosScreen(
                cursoParaAbrir = cursoPorAbrir,
                onCursoAbierto = mainViewModel::cursoAbierto,
                onModuloClick = { videoId ->
                    mainViewModel.irADetalle(videoId)
                },
                onNavigateTab = { ruta -> mainViewModel.irAPestana(ruta) }
            )
        }

        // Vista 7: Eventos y contacto
        composable(route = AppScreen.Eventos.route) {
            EventosScreen(
                onNavigateTab = { ruta -> mainViewModel.irAPestana(ruta) }
            )
        }

        // Vista 8: Perfil (Conectado a MainViewModel)
        composable(route = AppScreen.Perfil.route) {
            PerfilScreen(
                mainViewModel = mainViewModel,
                onMisGuardadosClick = { mainViewModel.navigateTo(AppScreen.Guardados.route) },
                onEditarPerfilClick = { /* Abre el cuadro interno de editar perfil */ },
                onAccesibilidadClick = { mainViewModel.navigateTo(AppScreen.Accesibilidad.route) },
                onCerrarSesionClick = { mainViewModel.cerrarSesion() },
                onNavigateTab = { ruta -> mainViewModel.irAPestana(ruta) }
            )
        }

        // Vista 9: Accesibilidad
        composable(route = AppScreen.Accesibilidad.route) {
            AccesibilidadScreen(
                onVolverClick = { mainViewModel.navigateBack() }
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

        // Sobre Master Martini (desde el menú lateral)
        composable(route = AppScreen.Sobre.route) {
            SobreMasterMartiniScreen(onVolverClick = { mainViewModel.navigateBack() })
        }
    }
}