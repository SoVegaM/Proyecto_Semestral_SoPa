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
 * Pantalla Mediana (Tablets pequeñas, teléfonos plegables o smartphones en horizontal).
 */
@Composable
fun HomeScreenMediana(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val estadisticas = viewModel.estadisticas
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Column {
                // 1. Barra superior
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = VerdeFondo
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo Master Martini",
                            modifier = Modifier.height(34.dp),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "Portal Técnico Gastronómico",
                            fontSize = 12.sp,
                            color = NegroLetra.copy(alpha = 0.75f),
                            fontWeight = FontWeight.Medium
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
            // Cabecera institucional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Capacitación Técnica y Recetario Profesional",
                        color = VerdeLetras,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Accede a cursos especializados en chocolatería, pastelería y masas de hoja desarrollados para potenciar tu emprendimiento gastronómico.",
                        fontSize = 13.sp,
                        color = NegroLetra,
                        lineHeight = 18.sp
                    )
                }
            }

            // Chips interactivos de Categorías
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




            // Grilla de 2 columnas de Cursos y Recetas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Catálogo ($categoriaSeleccionada)",
                    color = VerdeLetras,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${contenidos.size} contenidos",
                    color = NegroLetra.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }

            val filas = contenidos.chunked(2)
            filas.forEach { fila ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    fila.forEach { item ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(),
                            colors = CardDefaults.cardColors(containerColor = Blanco),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(14.dp)
                                    .fillMaxWidth(),
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
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "⏱ ${item.duracionOPreparacion}",
                                        color = NegroLetra.copy(alpha = 0.6f),
                                        fontSize = 11.sp,
                                        maxLines = 1,
                                        softWrap = false
                                    )
                                }

                                Text(
                                    text = item.titulo,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NegroLetra
                                )

                                Text(
                                    text = item.descripcion,
                                    fontSize = 12.sp,
                                    color = NegroLetra.copy(alpha = 0.8f),
                                    lineHeight = 16.sp
                                )

                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    color = GrisFondoSuave
                                )

                                Text(
                                    text = "Insumo: ${item.insumoDestacado}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VerdeLetras,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                    if (fila.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
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

@Preview(name = "Medium", widthDp = 700, heightDp = 1000, showBackground = true)
@Composable
fun PreviewMediana() {
    App_GrupoSoPaTheme {
        HomeScreenMediana()
    }
}
