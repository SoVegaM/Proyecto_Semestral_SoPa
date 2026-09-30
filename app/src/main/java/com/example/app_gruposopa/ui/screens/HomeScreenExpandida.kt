package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.app_gruposopa.model.TipoContenido
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.viewmodel.HomeViewModel

/**
 * Pantalla Expandida (Tablets apaisadas o pantallas de escritorio, ancho >= 840dp).
 */
@Composable
fun HomeScreenExpandida(
    viewModel: HomeViewModel = remember { HomeViewModel() }
) {
    val contenidos by viewModel.contenidosFiltrados.collectAsState()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsState()
    val estadisticas = viewModel.estadisticas
    val rightScrollState = rememberScrollState()

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
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Master Martini",
                            color = LetrasEnFondoVerde,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
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
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo Master Martini",
                            modifier = Modifier.height(36.dp),
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
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .navigationBarsPadding()
        ) {
            //PANEL LATERAL IZQUIERDO
            Surface(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight(),
                color = Blanco,
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Especialidades",
                        color = VerdeLetras,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Filtros por área gastronómica (conectados al ViewModel)
                    viewModel.categorias.forEach { cat ->
                        FilterChip(
                            selected = (cat == categoriaSeleccionada),
                            onClick = { viewModel.seleccionarCategoria(cat) },
                            label = {
                                Text(
                                    text = cat,
                                    color = if (cat == categoriaSeleccionada) Blanco else NegroLetra,
                                    fontSize = 12.sp,
                                    maxLines = 1
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = VerdeFondo,
                                containerColor = GrisFondoSuave
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    HorizontalDivider(color = GrisFondoSuave)

                    // Módulo de Escuela Unica
                    Card(
                        colors = CardDefaults.cardColors(containerColor = VerdeFondo),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Escuela Unica",
                                color = Dorado,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Formación técnica profesional creada para promover la excelencia de lo Hecho en Italia.",
                                color = LetrasEnFondoVerde,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Visita Escuela Unica", color = Blanco, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f, fill = false))

                    // Logo en el panel lateral
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        color = VerdeFondo
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.italia),
                                contentDescription = "Sello Hecho en Italia",
                                modifier = Modifier.height(34.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }

            //ANEL PRINCIPAL DERECHO
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .verticalScroll(rightScrollState)
                    .padding(20.dp),
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
                            text = "Catálogo Técnico de Panadería, Pastelería y Chocolatería",
                            color = VerdeLetras,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Fichas técnicas, recetas paso a paso y cursos en video desarrollados por el equipo técnico de Master Martini Chile.",
                            fontSize = 13.sp,
                            color = NegroLetra,
                            lineHeight = 18.sp
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
                        text = "Mostrando: $categoriaSeleccionada",
                        color = VerdeLetras,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${contenidos.size} contenidos disponibles",
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
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
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

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Insumo: ${item.insumoDestacado}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = VerdeLetras,
                                            modifier = Modifier.weight(1f, fill = false),
                                            maxLines = 2
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Button(
                                            onClick = { /* Ver detalle */ },
                                            colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                                            shape = RoundedCornerShape(4.dp),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            val textoBoton = when (item.tipo) {
                                                TipoContenido.RECETA -> "Ver Receta"
                                                TipoContenido.VIDEO_TECNICO -> "Video"
                                                TipoContenido.CURSO_ESPECIALIDAD -> "Curso"
                                            }
                                            Text(textoBoton, color = Blanco, fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                        if (fila.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Expanded", widthDp = 1000, heightDp = 1000, showBackground = true)
@Composable
fun PreviewExpandida() {
    App_GrupoSoPaTheme {
        HomeScreenExpandida()
    }
}
