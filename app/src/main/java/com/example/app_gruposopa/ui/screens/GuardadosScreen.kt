package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.FiltroGuardados
import com.example.app_gruposopa.ui.viewmodels.GuardadosViewModel

/**
 * Vista 10 · Mis guardados (Favoritos)
 * Conectada a GuardadosViewModel (guardados persistidos con DataStore):
 * - Barra superior: ← Mis guardados
 * - Filtros rápidos: Todos / Recetas / Videos
 * - Lista de tarjetas con título, tipo, duración e ícono para quitar guardado
 * - Estado vacío con mensaje claro ("Aún no guardas contenidos")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuardadosScreen(
    viewModel: GuardadosViewModel = viewModel(),
    onVolverClick: () -> Unit = {},
    onVideoClick: (String) -> Unit = {}
) {
    val filtroSeleccionado by viewModel.filtro.collectAsState()
    val listaGuardados by viewModel.guardados.collectAsState()
    val guardadosFiltrados by viewModel.guardadosFiltrados.collectAsState()

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis guardados",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Blanco
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdeFondo
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
        ) {
            // 1. Fila de Filtros (Chips: Todos, Recetas, Videos)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blanco)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FiltroGuardados.entries.forEach { filtro ->
                    val esSeleccionado = (filtro == filtroSeleccionado)
                    FilterChip(
                        selected = esSeleccionado,
                        onClick = { viewModel.seleccionarFiltro(filtro) },
                        label = {
                            Text(
                                text = filtro.etiqueta,
                                fontSize = 13.sp,
                                fontWeight = if (esSeleccionado) FontWeight.Bold else FontWeight.Normal,
                                color = if (esSeleccionado) Blanco else NegroLetra
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = VerdeFondo,
                            containerColor = GrisFondoSuave
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            HorizontalDivider(color = GrisFondoSuave)

            // 2. Contenido principal: Lista o Estado Vacío
            if (guardadosFiltrados.isEmpty()) {
                // Estado vacío requerido por la pauta
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = null,
                            tint = Dorado,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "Aún no guardas contenidos",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = VerdeLetras,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (listaGuardados.isEmpty()) {
                                "Explora las recetas, videos y cursos técnicos en el catálogo y guárdalos para acceder rápidamente a ellos."
                            } else {
                                "No tienes contenidos guardados en la categoría \"${filtroSeleccionado.etiqueta}\"."
                            },
                            fontSize = 13.sp,
                            color = NegroLetra.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
            } else {
                // Lista de guardados
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    guardadosFiltrados.forEach { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            onClick = { onVideoClick(item.id) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Título y metadata
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = item.titulo,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = NegroLetra
                                    )
                                    Text(
                                        text = "${item.tipo.etiqueta} · ${item.duracion}",
                                        fontSize = 12.sp,
                                        color = NegroLetra.copy(alpha = 0.6f)
                                    )
                                }

                                // Botón para quitar de guardados (accesible >= 48dp)
                                IconButton(
                                    onClick = { viewModel.quitarGuardado(item.id) },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Quitar ${item.titulo} de guardados",
                                        tint = Dorado,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Vista 10 - Guardados Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun GuardadosScreenPreview() {
    App_GrupoSoPaTheme {
        GuardadosScreen()
    }
}
