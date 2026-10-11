package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Schedule
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
import com.example.app_gruposopa.R
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.HomeViewModel

/**
 * Vista 4 - Inicio (Centro técnico)
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = remember { HomeViewModel() },
    onContenidoClick: (ContenidoMasterMartini) -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                // Barra superior verde
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VerdeFondo)
                        .statusBarsPadding()
                )

                // Barra blanca con logo Master Martini
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
                                .height(45.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        },
        bottomBar = {
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
                        onClick = { /* Ya está en Inicio */ },
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Encabezado y controles
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
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

                    // Buscador
                    OutlinedTextField(
                        value = textoBusqueda,
                        onValueChange = { viewModel.onTextoBusquedaChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 52.dp),
                        placeholder = {
                            Text(
                                text = "Buscar receta o curso",
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

                    Text(
                        text = "Especialidades",
                        color = VerdeLetras,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Chips de filtro por Categoría
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.categorias) { cat ->
                            FilterChip(
                                selected = (cat == categoriaSeleccionada),
                                onClick = { viewModel.seleccionarCategoria(cat) },
                                label = {
                                    Text(
                                        text = cat,
                                        fontSize = 12.sp,
                                        color = if (cat == categoriaSeleccionada) Blanco else NegroLetra
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VerdeFondo,
                                    containerColor = Blanco
                                )
                            )
                        }
                    }

                    // Contador de resultados
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
                }
            }

            // Caso sin resultados
            if (contenidos.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Blanco),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No se encontraron contenidos para tu búsqueda.",
                                color = NegroLetra.copy(alpha = 0.7f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            } else {
                // Lista de tarjetas
                items(contenidos, key = { it.titulo }) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Blanco),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        onClick = { onContenidoClick(item) }
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Schedule,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = NegroLetra.copy(alpha = 0.6f)
                                    )
                                    Text(
                                        text = item.duracionOPreparacion,
                                        color = NegroLetra.copy(alpha = 0.6f),
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                }
                            }

                            Text(
                                text = item.titulo,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = NegroLetra
                            )

                            Text(
                                text = item.descripcion,
                                fontSize = 13.sp,
                                color = NegroLetra.copy(alpha = 0.85f),
                                lineHeight = 18.sp
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = GrisFondoSuave
                            )

                            Text(
                                text = "Insumo destacado: ${item.insumoDestacado}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VerdeLetras
                            )
                        }
                    }
                }
            }

            // Pie de página con logo
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    color = VerdeFondo
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.italia),
                            contentDescription = "Sello Hecho en Italia",
                            modifier = Modifier.height(38.dp),
                            contentScale = ContentScale.Fit
                        )
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