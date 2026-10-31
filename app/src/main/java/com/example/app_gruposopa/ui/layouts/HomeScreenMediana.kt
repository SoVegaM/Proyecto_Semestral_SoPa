package com.example.app_gruposopa.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
 * Inicio para tablet chica o celular girado, en 2 columnas.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreenMediana(
    viewModel: HomeViewModel = remember { HomeViewModel() },
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onGuardadosClick: () -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    Scaffold(
        topBar = { BarraSuperiorInicio(onGuardadosClick, onMenuClick) },
        bottomBar = {
            BarraNavegacionInferior(
                rutaActual = AppScreen.Inicio.route,
                onNavegar = onNavigateTab
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) { EncabezadoInicio() }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f)) {
                        BuscadorContenidos(textoBusqueda, viewModel::onTextoBusquedaChange)
                    }
                    Box(Modifier.weight(1f)) {
                        BannerEscuelaUnica(onClick = { onNavigateTab(AppScreen.Cursos.route) })
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) { TituloSeccion("Especialidades") }
            item(span = { GridItemSpan(maxLineSpan) }) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
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
            item(span = { GridItemSpan(maxLineSpan) }) { ContadorResultados(contenidos.size) }
            if (contenidos.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) { SinResultados() }
            } else {
                items(contenidos, key = { it.id }) { item ->
                    TarjetaContenido(
                        item = item,
                        onClick = { onContenidoClick(item) },
                        alturaMiniatura = 120.dp
                    )
                }
            }
        }
    }
}

@Preview(name = "Inicio - Mediana", widthDp = 700, heightDp = 900, showBackground = true)
@Composable
fun PreviewHomeMediana() {
    App_GrupoSoPaTheme {
        HomeScreenMediana()
    }
}
