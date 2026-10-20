package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel

/**
 * Vista 4 · Inicio (Centro Técnico Gastronómico)
 * Conectada al repositorio central en memoria:
 * - 30 videos reales de YouTube de Master Martini
 * - Chips de categorías generadas dinámicamente desde los datos (Todas + 4)
 * - Buscador interactivo que filtra por título y producto
 * - Contador "N encontrados"
 * - Miniaturas reales de YouTube mediante Coil
 * - Navegación al tocar una tarjeta hacia "detalle/{videoId}"
 * - Responsive: 1 columna (compacta), 2 columnas (mediana) y 3 columnas (expandida)
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = remember { HomeViewModel() },
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    Scaffold(
        topBar = {
            Column {
                // 1. Barra superior verde corporativa
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = VerdeFondo
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 14.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Master Martini",
                            color = LetrasEnFondoVerde,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // 2. Barra blanca con logo Master Martini
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Blanco,
                    shadowElevation = 2.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
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

                        // Acceso directo a Vista 10 · Mis guardados (Persona A)
                        IconButton(
                            onClick = { onNavigateTab("guardados") },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                                .size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Mis guardados",
                                tint = Dorado
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Barra de navegación inferior
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                color = Blanco,
                shadowElevation = 8.dp
            ) {
                NavigationBar(
                    containerColor = Blanco,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { /* Ya en Inicio */ },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeLetras,
                            selectedTextColor = VerdeLetras,
                            indicatorColor = VerdeFondo.copy(alpha = 0.12f)
                        )
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { onNavigateTab("cursos") },
                        icon = { Icon(Icons.Default.School, contentDescription = "Cursos") },
                        label = { Text("Cursos", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = NegroLetra.copy(alpha = 0.6f),
                            unselectedTextColor = NegroLetra.copy(alpha = 0.6f)
                        )
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { onNavigateTab("eventos") },
                        icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Eventos") },
                        label = { Text("Eventos", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = NegroLetra.copy(alpha = 0.6f),
                            unselectedTextColor = NegroLetra.copy(alpha = 0.6f)
                        )
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { onNavigateTab("perfil") },
                        icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                        label = { Text("Perfil", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = NegroLetra.copy(alpha = 0.6f),
                            unselectedTextColor = NegroLetra.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
        ) {
            // Determinar cantidad de columnas según ancho disponible (1 compacta, 2 mediana, 3 expandida)
            val columnas = when {
                maxWidth >= 840.dp -> 3
                maxWidth >= 600.dp -> 2
                else -> 1
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Título institucional
                Text(
                    text = "Centro Técnico Gastronómico",
                    color = VerdeLetras,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Cursos, recetas y asesoría técnica en materias primas para panaderías, pastelerías y chocolaterías.",
                    fontSize = 13.sp,
                    color = NegroLetra,
                    lineHeight = 18.sp
                )

                // Banner destacado hacia Vista 6: Cursos (Escuela Única)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onNavigateTab("cursos") },
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
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "6 cursos de formación profesional",
                                    color = Blanco,
                                    fontSize = 13.sp,
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
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Buscador por texto (filtra por título y producto)
                OutlinedTextField(
                    value = textoBusqueda,
                    onValueChange = { viewModel.onTextoBusquedaChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    placeholder = {
                        Text(
                            text = "Buscar por receta, técnica o producto...",
                            fontSize = 13.sp,
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

                // Chips de categorías generados desde los datos reales (Todas + 4)
                Text(
                    text = "Especialidades",
                    color = VerdeLetras,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

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
                                    fontSize = 12.sp,
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

                // Encabezado con contador
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Contenidos Disponibles",
                        color = VerdeLetras,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${contenidos.size} encontrados",
                        color = NegroLetra.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }

                // Cuadrícula responsive de tarjetas de videos
                if (contenidos.isEmpty()) {
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
                                fontSize = 13.sp
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columnas),
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
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
                                    // Miniatura oficial de YouTube mediante Coil
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
                                                color = Dorado,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                maxLines = 1
                                            )
                                        }
                                        Text(
                                            text = "⏱ ${item.duracion}",
                                            color = NegroLetra.copy(alpha = 0.6f),
                                            fontSize = 11.sp
                                        )
                                    }

                                    // Título
                                    Text(
                                        text = item.titulo,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = NegroLetra,
                                        maxLines = 2
                                    )

                                    // Descripción ("Video con {producto}")
                                    Text(
                                        text = item.descripcion,
                                        fontSize = 12.sp,
                                        color = NegroLetra.copy(alpha = 0.8f),
                                        lineHeight = 16.sp,
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
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = VerdeLetras,
                                            maxLines = 1,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "Ver video ▶",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Dorado
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
}

/**
 * Alias de compatibilidad para mantener referencias hacia HomeScreenAdaptable
 */
@Composable
fun HomeScreenAdaptable(
    viewModel: HomeViewModel = remember { HomeViewModel() },
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    HomeScreen(
        viewModel = viewModel,
        onContenidoClick = onContenidoClick,
        onNavigateTab = onNavigateTab
    )
}

@Preview(name = "Vista 4 - Inicio Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun HomeScreenPreview() {
    App_GrupoSoPaTheme {
        HomeScreen()
    }
}
