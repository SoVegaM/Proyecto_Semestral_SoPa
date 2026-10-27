package com.example.app_gruposopa.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.*
import com.example.app_gruposopa.ui.theme.App_GrupoSoPaTheme
import com.example.app_gruposopa.ui.theme.GrisFondoSuave
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel

/**
 * Inicio para celular, en una columna.
 */
@Composable
fun HomeScreenCompacta(
    viewModel: HomeViewModel = remember { HomeViewModel() },
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onGuardadosClick: () -> Unit = {}
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    Scaffold(
        topBar = { BarraSuperiorInicio(onGuardadosClick) },
        bottomBar = {
            BarraNavegacionInferior(
                rutaActual = AppScreen.Inicio.route,
                onNavegar = onNavigateTab
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { EncabezadoInicio() }
            item { BannerEscuelaUnica(onClick = { onNavigateTab(AppScreen.Cursos.route) }) }
            item { BuscadorContenidos(textoBusqueda, viewModel::onTextoBusquedaChange) }
            item { TituloSeccion("Especialidades") }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.categorias.forEach { cat ->
                        ChipCategoria(
                            categoria = cat,
                            seleccionada = cat == categoriaSeleccionada,
                            onClick = { viewModel.seleccionarCategoria(cat) }
                        )
                    }
                }
            }
            item { ContadorResultados(contenidos.size) }
            if (contenidos.isEmpty()) {
                item { SinResultados() }
            } else {
                items(contenidos, key = { it.id }) { item ->
                    TarjetaContenido(item = item, onClick = { onContenidoClick(item) })
                }
            }
        }
    }
}

@Preview(name = "Inicio - Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun PreviewHomeCompacta() {
    App_GrupoSoPaTheme {
        HomeScreenCompacta()
    }
}
