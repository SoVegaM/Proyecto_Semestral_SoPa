package com.example.app_gruposopa.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gruposopa.domain.model.Curso
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.BarraNavegacionInferior
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.CursosViewModel

/**
 * Vista 6: Cursos (Escuela Única).
 * Al tocar un curso se ven sus módulos; cada módulo abre su video.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursosScreen(
    viewModel: CursosViewModel = remember { CursosViewModel() },
    onModuloClick: (String) -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val videosVistos by viewModel.videosVistos.collectAsState()
    val cursosExpandidos by viewModel.cursosExpandidos.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Escuela Única",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdeFondo
                )
            )
        },
        bottomBar = {
            BarraNavegacionInferior(
                rutaActual = AppScreen.Cursos.route,
                onNavegar = onNavigateTab
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Encabezado
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Formación Profesional",
                        color = VerdeLetras,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Formación profesional con metodología Hecho en Italia.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NegroLetra.copy(alpha = 0.8f)
                    )
                }
            }

            // Título con la cantidad de cursos
            Text(
                text = "Cursos disponibles (${viewModel.cursos.size})",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = VerdeLetras,
                modifier = Modifier.padding(top = 4.dp, start = 2.dp)
            )

            // Lista de cursos
            viewModel.cursos.forEach { curso ->
                val modulos = viewModel.obtenerModulos(curso)
                val totales = curso.videoIds.size
                val completados = curso.videoIds.count { videosVistos.contains(it) }
                val progreso = if (totales > 0) completados.toFloat() / totales else 0f
                val estado = when {
                    completados == 0 -> "Sin iniciar"
                    completados >= totales -> "Completado"
                    else -> "En curso"
                }
                val expandido = cursosExpandidos.contains(curso.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { viewModel.toggleExpandido(curso.id) },
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Título, estado y flecha
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = curso.titulo,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = NegroLetra,
                                modifier = Modifier.weight(1f)
                            )

                            // Estado del curso
                            val (colorFondo, colorTexto) = when (estado) {
                                "Completado" -> Pair(VerdeFondo.copy(alpha = 0.12f), VerdeLetras)
                                "En curso" -> Pair(Dorado.copy(alpha = 0.18f), DoradoTexto)
                                else -> Pair(GrisFondoSuave, NegroLetra.copy(alpha = 0.6f))
                            }

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = colorFondo
                            ) {
                                Text(
                                    text = estado,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorTexto,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(
                                imageVector = if (expandido) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = if (expandido) "Colapsar módulos" else "Ver módulos",
                                tint = NegroLetra.copy(alpha = 0.5f),
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Text(
                            text = curso.descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            color = NegroLetra.copy(alpha = 0.75f)
                        )

                        // Módulos y completados
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$totales módulos",
                                style = MaterialTheme.typography.bodyMedium,
                                color = NegroLetra.copy(alpha = 0.65f)
                            )
                            Text(
                                text = "$completados de $totales completados",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = NegroLetra.copy(alpha = 0.7f)
                            )
                        }

                        // Progreso
                        LinearProgressIndicator(
                            progress = { progreso },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Dorado,
                            trackColor = GrisFondoSuave
                        )

                        // Módulos expandidos
                        AnimatedVisibility(
                            visible = expandido,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                HorizontalDivider(color = GrisFondoSuave)

                                Text(
                                    text = "Módulos del curso (toca para ver):",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VerdeLetras
                                )

                                modulos.forEachIndexed { index, modulo ->
                                    val visto = videosVistos.contains(modulo.id)
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(6.dp))
                                            .clickable { onModuloClick(modulo.id) },
                                        color = if (visto) VerdeFondo.copy(alpha = 0.05f) else GrisFondoSuave
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 10.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(
                                                modifier = Modifier.weight(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                Icon(
                                                    imageVector = if (visto) Icons.Default.CheckCircle else Icons.Outlined.PlayCircleOutline,
                                                    contentDescription = if (visto) "Módulo visto" else "Módulo pendiente",
                                                    tint = if (visto) VerdeLetras else Dorado,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Column {
                                                    Text(
                                                        text = "${index + 1}. ${modulo.titulo}",
                                                        style = MaterialTheme.typography.bodyMedium,
                                                        fontWeight = if (visto) FontWeight.SemiBold else FontWeight.Normal,
                                                        color = NegroLetra
                                                    )
                                                    Text(
                                                        text = "Insumo: ${modulo.producto}",
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = NegroLetra.copy(alpha = 0.6f)
                                                    )
                                                }
                                            }
                                            Text(
                                                text = modulo.duracion,
                                                style = MaterialTheme.typography.labelMedium,
                                                color = NegroLetra.copy(alpha = 0.6f),
                                                modifier = Modifier.padding(start = 6.dp)
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
}

@Preview(name = "Vista 6 - Cursos Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun CursosScreenPreview() {
    App_GrupoSoPaTheme {
        CursosScreen()
    }
}
