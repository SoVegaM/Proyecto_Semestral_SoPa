package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gruposopa.R
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.viewmodel.HomeViewModel

/**
 * Pantalla Compacta (Smartphones verticales).
 */
@Composable
fun HomeScreenCompacta(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val estadisticas = viewModel.estadisticas
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Column {
                // 1. Barra superior verde
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

                // 2. Barra blanca con el logo Master Martini
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
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(GrisFondoSuave)
                .navigationBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bienvenida concisa para App Móvil
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



            // Filtros interactivos por Categoría
            Text(
                text = "Especialidades",
                color = VerdeLetras,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.categorias.forEach { cat ->
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

            // Catálogo reactivo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contenidos Disponibles",
                    color = VerdeLetras,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${contenidos.size} encontrados",
                    color = NegroLetra.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }

            contenidos.forEach { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                            Text(
                                text = "⏱ ${item.duracionOPreparacion}",
                                color = NegroLetra.copy(alpha = 0.6f),
                                fontSize = 11.sp,
                                softWrap = false,
                                maxLines = 1
                            )
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

            // Pie de página móvil con el logo
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

@Preview(name = "Compact", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun PreviewCompact() {
    App_GrupoSoPaTheme {
        HomeScreenCompacta()
    }
}
