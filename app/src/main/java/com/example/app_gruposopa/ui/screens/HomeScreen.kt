package com.example.app_gruposopa.ui.screens

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.ui.layouts.HomeScreenCompacta
import com.example.app_gruposopa.ui.layouts.HomeScreenExpandida
import com.example.app_gruposopa.ui.layouts.HomeScreenMediana
import com.example.app_gruposopa.ui.utils.obtenerWindowSizeClass
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel

/**
 * Vista 4: Inicio. Elige el diseño según el tamaño de la pantalla.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onGuardadosClick: () -> Unit = {}
) {
    val windowSizeClass = obtenerWindowSizeClass()
    val ancho = windowSizeClass.widthSizeClass
    val alto = windowSizeClass.heightSizeClass
    when {
        ancho == WindowWidthSizeClass.Compact -> HomeScreenCompacta(viewModel, onContenidoClick, onNavigateTab, onGuardadosClick)
        // El celular girado usa el mediano porque es muy bajo para el panel
        ancho == WindowWidthSizeClass.Expanded && alto != WindowHeightSizeClass.Compact ->
            HomeScreenExpandida(viewModel, onContenidoClick, onNavigateTab, onGuardadosClick)
        else -> HomeScreenMediana(viewModel, onContenidoClick, onNavigateTab, onGuardadosClick)
    }
}
