package com.example.app_gruposopa.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.app_gruposopa.ui.theme.Blanco
import com.example.app_gruposopa.ui.theme.GrisFondoSuave
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel

/**
 * Inicio para tablet: filtros a la izquierda y videos a la derecha.
 */
@Composable
fun HomeScreenExpandida(
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
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Panel de filtros
            Column(
                modifier = Modifier
                    .width(340.dp)
                    .fillMaxHeight()
                    .background(Blanco)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EncabezadoInicio()
                BuscadorContenidos(textoBusqueda, viewModel::onTextoBusquedaChange)
                TituloSeccion("Especialidades")
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    viewModel.categorias.forEach { cat ->
                        ChipCategoria(
                            categoria = cat,
                            seleccionada = cat == categoriaSeleccionada,
                            onClick = { viewModel.seleccionarCategoria(cat) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                BannerEscuelaUnica(onClick = { onNavigateTab(AppScreen.Cursos.route) })
            }

            // Resultados
            LazyVerticalGrid(
                // Las columnas que quepan (en tablet son 3)
                columns = GridCells.Adaptive(minSize = 260.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(GrisFondoSuave),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) { ContadorResultados(contenidos.size) }
                if (contenidos.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) { SinResultados() }
                } else {
                    items(contenidos, key = { it.id }) { item ->
                        TarjetaContenido(
                            item = item,
                            onClick = { onContenidoClick(item) },
                            alturaMiniatura = 130.dp
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Inicio - Expandida", widthDp = 1280, heightDp = 800, showBackground = true)
@Composable
fun PreviewHomeExpandida() {
    App_GrupoSoPaTheme {
        HomeScreenExpandida()
    }
}
