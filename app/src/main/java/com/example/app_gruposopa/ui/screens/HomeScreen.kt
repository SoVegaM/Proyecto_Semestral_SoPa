package com.example.app_gruposopa.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.ui.components.MenuLateral
import com.example.app_gruposopa.ui.components.OpcionMenu
import com.example.app_gruposopa.ui.layouts.HomeScreenCompacta
import com.example.app_gruposopa.ui.layouts.HomeScreenExpandida
import com.example.app_gruposopa.ui.layouts.HomeScreenMediana
import com.example.app_gruposopa.ui.theme.Blanco
import com.example.app_gruposopa.ui.theme.VerdeFondo
import com.example.app_gruposopa.ui.theme.VerdeLetras
import com.example.app_gruposopa.ui.utils.obtenerWindowSizeClass
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

/**
 * Vista 4: Inicio. Elige el diseño según el tamaño de la pantalla.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onGuardadosClick: () -> Unit = {},
    onOpcionMenu: (OpcionMenu, ContenidoMasterMartini?) -> Unit = { _, _ -> }
) {
    val windowSizeClass = obtenerWindowSizeClass()
    val ancho = windowSizeClass.widthSizeClass
    val alto = windowSizeClass.heightSizeClass
    val cargando by viewModel.cargando.collectAsState()
    val ultimoVisto by viewModel.ultimoVisto.collectAsState()

    // Menú lateral
    val estadoMenu = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val abrirMenu: () -> Unit = { scope.launch { estadoMenu.open() } }

    // Con el menú abierto, Atrás lo cierra en vez de salir de Inicio
    BackHandler(enabled = estadoMenu.isOpen) {
        scope.launch { estadoMenu.close() }
    }

    ModalNavigationDrawer(
        drawerState = estadoMenu,
        // Solo se cierra deslizando; así deslizar las categorías no lo abre sin querer
        gesturesEnabled = estadoMenu.isOpen,
        drawerContent = {
            MenuLateral(
                nombreUsuario = "Invitado",
                ultimoVisto = ultimoVisto?.titulo,
                onOpcion = { opcion ->
                    scope.launch {
                        estadoMenu.close()
                        onOpcionMenu(opcion, ultimoVisto)
                    }
                }
            )
        }
    ) {
        // Fundido entre la carga y el contenido
        Crossfade(targetState = cargando, animationSpec = tween(500), label = "cargaInicio") { estaCargando ->
            when {
                estaCargando -> CargandoInicio()
                ancho == WindowWidthSizeClass.Compact ->
                    HomeScreenCompacta(viewModel, onContenidoClick, onNavigateTab, onGuardadosClick, abrirMenu)
                // El celular girado usa el mediano porque es muy bajo para el panel
                ancho == WindowWidthSizeClass.Expanded && alto != WindowHeightSizeClass.Compact ->
                    HomeScreenExpandida(viewModel, onContenidoClick, onNavigateTab, onGuardadosClick, abrirMenu)
                else -> HomeScreenMediana(viewModel, onContenidoClick, onNavigateTab, onGuardadosClick, abrirMenu)
            }
        }
    }
}

@Composable
private fun CargandoInicio() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Master Martini",
            modifier = Modifier.height(80.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressIndicator(color = VerdeFondo)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Cargando contenidos...",
            style = MaterialTheme.typography.bodyLarge,
            color = VerdeLetras
        )
    }
}
