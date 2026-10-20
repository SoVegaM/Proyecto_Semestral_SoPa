package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_gruposopa.R
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.BarraNavegacionInferior
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel

/**
 * Vista 4: Inicio. Buscador, filtro por categoría y lista de videos.
 * Usa 1, 2 o 3 columnas según el ancho de la pantalla.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = remember { HomeViewModel() },
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {},
    onGuardadosClick: () -> Unit = {}
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    Scaffold(
        topBar = {
            Column {
                // Barra blanca con logo Master Martini
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Blanco,
                    shadowElevation = 2.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo Master Martini",
                            modifier = Modifier
                                .height(34.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )

                        // Ir a Mis guardados
                        IconButton(
                            onClick = onGuardadosClick,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Mis guardados",
                                tint = DoradoTexto
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            BarraNavegacionInferior(
                rutaActual = AppScreen.Inicio.route,
                onNavegar = onNavigateTab
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
        ) {
            // Columnas según el ancho
            val columnas = when {
                maxWidth >= 840.dp -> 3
                maxWidth >= 600.dp -> 2
                else -> 1
            }

            // Todo va dentro de la grilla para que haga scroll la pantalla completa
            LazyVerticalGrid(
                columns = GridCells.Fixed(columnas),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Centro Técnico Gastronómico",
                            color = VerdeLetras,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Cursos, recetas y asesoría técnica en materias primas para panaderías, pastelerías y chocolaterías.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NegroLetra
                        )
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    // Banner que lleva a Cursos
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onNavigateTab(AppScreen.Cursos.route) },
                        colors = CardDefaults.cardColors(containerColor = VerdeFondo),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.School,
                                    contentDescription = null,
                                    tint = Dorado,
                                    modifier = Modifier.size(26.dp)
                                )
                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = "ESCUELA ÚNICA",
                                        color = Dorado,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                    Text(
                                        text = "6 cursos de formación profesional",
                                        color = Blanco,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Dorado
                            ) {
                                Text(
                                    text = "Ver Cursos →",
                                    color = NegroLetra,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    // Buscador (por título o producto)
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { viewModel.onTextoBusquedaChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 52.dp),
                        placeholder = {
                            Text(
                                text = "Buscar receta o producto",
                                style = MaterialTheme.typography.bodyMedium,
                                color = NegroLetra.copy(alpha = 0.5f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = VerdeLetras
                            )
                        },
                        trailingIcon = {
                            if (textoBusqueda.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onTextoBusquedaChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Limpiar búsqueda",
                                        tint = NegroLetra.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Blanco,
                            unfocusedContainerColor = Blanco,
                            focusedBorderColor = VerdeFondo,
                            unfocusedBorderColor = GrisFondoSuave
                        )
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    // Categorías
                    Text(
                        text = "Especialidades",
                        color = VerdeLetras,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.categorias.forEach { cat ->
                            val esSeleccionada = (cat == categoriaSeleccionada)
                            FilterChip(
                                selected = esSeleccionada,
                                onClick = { viewModel.seleccionarCategoria(cat) },
                                label = {
                                    Text(
                                        text = cat,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = if (esSeleccionada) FontWeight.Bold else FontWeight.Normal,
                                        color = if (esSeleccionada) Blanco else NegroLetra
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VerdeFondo,
                                    containerColor = Blanco
                                )
                            )
                        }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    // Encabezado con contador
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contenidos Disponibles",
                            color = VerdeLetras,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${contenidos.size} encontrados",
                            color = NegroLetra.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                if (contenidos.isEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No se encontraron videos para tu búsqueda.",
                                    color = NegroLetra.copy(alpha = 0.7f),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                } else {
                    items(contenidos, key = { it.id }) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            onClick = { onContenidoClick(item) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Miniatura del video (Coil)
                                AsyncImage(
                                    model = item.miniaturaUrl,
                                    contentDescription = "Miniatura de ${item.titulo}",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .clip(RoundedCornerShape(6.dp)),
                                    contentScale = ContentScale.Crop
                                )

                                // Fila de categoría y duración
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = Dorado.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = item.categoria,
                                            color = DoradoTexto,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            maxLines = 1
                                        )
                                    }
                                    Text(
                                        text = "⏱ ${item.duracion}",
                                        color = NegroLetra.copy(alpha = 0.6f),
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }

                                Text(
                                    text = item.titulo,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = NegroLetra,
                                    maxLines = 2
                                )

                                Text(
                                    text = item.descripcion,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = NegroLetra.copy(alpha = 0.8f),
                                    maxLines = 2
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    color = GrisFondoSuave
                                )

                                // Insumo destacado y acción
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Insumo: ${item.producto}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = VerdeLetras,
                                        maxLines = 1,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "Ver video ▶",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = DoradoTexto
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

@Preview(name = "Vista 4 - Inicio Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun HomeScreenPreview() {
    App_GrupoSoPaTheme {
        HomeScreen()
    }
}
