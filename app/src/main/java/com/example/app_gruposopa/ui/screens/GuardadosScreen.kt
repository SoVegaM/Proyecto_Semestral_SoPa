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
 * Vista 10: Mis guardados, con filtro Todos / Recetas / Técnicas.
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
                        style = MaterialTheme.typography.titleMedium,
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
            // Filtros
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
                                style = MaterialTheme.typography.bodyMedium,
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

            if (guardadosFiltrados.isEmpty()) {
                // Sin guardados
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
                            text = if (listaGuardados.isEmpty()) {
                                "Aún no guardas contenidos"
                            } else {
                                "No tienes ${filtroSeleccionado.etiqueta.lowercase()} guardadas"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = VerdeLetras,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = if (listaGuardados.isEmpty()) {
                                "Toca \"Guardar\" en cualquier receta o técnica para encontrarla aquí después."
                            } else {
                                "Toca \"Guardar\" en una ${if (filtroSeleccionado == FiltroGuardados.RECETAS) "receta" else "técnica"} para verla en esta lista."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = NegroLetra.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
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
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = item.titulo,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = NegroLetra
                                    )
                                    Text(
                                        text = "${item.tipo.etiqueta} - ${item.duracion}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = NegroLetra.copy(alpha = 0.6f)
                                    )
                                }

                                // Quitar (mínimo 48dp)
                                IconButton(
                                    onClick = { viewModel.quitarGuardado(item.id) },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Quitar ${item.titulo} de guardados",
                                        tint = DoradoTexto,
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
