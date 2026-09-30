package com.example.app_gruposopa.ui.screens

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.app_gruposopa.ui.utils.obtenerWindowSizeClass
import com.example.app_gruposopa.viewmodel.HomeViewModel

/**
 * Pantalla principal adaptable que determina la resolución del dispositivo
 * y despacha la interfaz correspondiente utilizando Window Size Classes de Material 3.
 *
 * Comparte la instancia de HomeViewModel entre las diferentes vistas para preservar
 * el estado (categoría seleccionada y filtros) sin importar la rotación o tamaño de pantalla.
 *
 *
 */
@Composable
fun HomeScreenAdaptable(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val windowSizeClass = obtenerWindowSizeClass()

    when {
        // Celular en modo horizontal: al rotar, la altura es compacta
        // Se utiliza la vista Mediana para aprovechar el ancho en 2 columnas sin apretar la pantalla.
        windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact -> {
            HomeScreenMediana(viewModel = viewModel)
        }
        // Celular en modo vertical estándar
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact -> {
            HomeScreenCompacta(viewModel = viewModel)
        }
        // Tablets en vertical o pantallas medianas/plegables
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium -> {
            HomeScreenMediana(viewModel = viewModel)
        }
        // Tablets grandes en horizontal o pantallas de escritorio
        windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded -> {
            HomeScreenExpandida(viewModel = viewModel)
        }
        else -> HomeScreenCompacta(viewModel = viewModel)
    }
}

@Composable
fun HomeScreen2(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    HomeScreenAdaptable(viewModel = viewModel)
}
