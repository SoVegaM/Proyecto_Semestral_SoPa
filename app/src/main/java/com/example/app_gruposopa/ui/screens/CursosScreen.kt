package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gruposopa.ui.theme.*

/**
 * Estado de avance del curso para el MVP.
 */
enum class EstadoCurso(val etiqueta: String) {
    EN_CURSO("En curso"),
    SIN_INICIAR("Sin iniciar"),
    COMPLETADO("Completado")
}

/**
 * Modelo de datos de presentación para los Cursos de Escuela Única.
 */
data class CursoItem(
    val id: Int,
    val titulo: String,
    val modulosTotales: Int,
    val modulosCompletados: Int,
    val categoria: String = "Escuela Única"
) {
    val progreso: Float
        get() = if (modulosTotales > 0) modulosCompletados.toFloat() / modulosTotales else 0f

    val estado: EstadoCurso
        get() = when {
            modulosCompletados == 0 -> EstadoCurso.SIN_INICIAR
            modulosCompletados >= modulosTotales -> EstadoCurso.COMPLETADO
            else -> EstadoCurso.EN_CURSO
        }
}

/**
 * Vista 6 · Cursos (Escuela Única)
 * Maqueta para celular compacto según el wireframe oficial:
 * - Barra superior: "Escuela Única" (#004D42)
 * - Subtítulo: "Formación profesional con metodología Hecho en Italia."
 * - Tarjetas de cursos con nombre, cantidad de módulos y barra de progreso dorada
 * - Barra de navegación inferior activa en "Cursos"
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursosScreen(
    onCursoClick: (CursoItem) -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    // Datos ficticios representativos del caso oficial
    val cursos = remember {
        listOf(
            CursoItem(
                id = 1,
                titulo = "Chocolatería profesional",
                modulosTotales = 6,
                modulosCompletados = 4
            ),
            CursoItem(
                id = 2,
                titulo = "Pastelería de vitrina",
                modulosTotales = 5,
                modulosCompletados = 1
            ),
            CursoItem(
                id = 3,
                titulo = "Bollería y masas de hoja",
                modulosTotales = 4,
                modulosCompletados = 0
            ),
            CursoItem(
                id = 4,
                titulo = "Gelatería artesanal italiana",
                modulosTotales = 5,
                modulosCompletados = 5
            )
        )
    }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Escuela Única",
                        fontSize = 18.sp,
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
            // Barra de navegación inferior compartida (Inicio, Cursos, Eventos, Perfil)
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
                        selected = false,
                        onClick = { onNavigateTab("inicio") },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                        label = { Text("Inicio", fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = NegroLetra.copy(alpha = 0.6f),
                            unselectedTextColor = NegroLetra.copy(alpha = 0.6f)
                        )
                    )
                    NavigationBarItem(
                        selected = true,
                        onClick = { /* Ya en Cursos */ },
                        icon = { Icon(Icons.Default.School, contentDescription = "Cursos") },
                        label = { Text("Cursos", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = VerdeLetras,
                            selectedTextColor = VerdeLetras,
                            indicatorColor = VerdeFondo.copy(alpha = 0.12f)
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Banner / Subtítulo oficial
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
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Formación profesional con metodología Hecho en Italia.",
                        fontSize = 13.sp,
                        color = NegroLetra.copy(alpha = 0.8f),
                        lineHeight = 18.sp
                    )
                }
            }

            // Título de la sección
            Text(
                text = "Cursos disponibles (${cursos.size})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeLetras,
                modifier = Modifier.padding(top = 4.dp, start = 2.dp)
            )

            // Lista de Cursos
            cursos.forEach { curso ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCursoClick(curso) },
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
                        // Fila superior: Título del curso y estado
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = curso.titulo,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = NegroLetra,
                                modifier = Modifier.weight(1f)
                            )

                            // Chip del Estado
                            val (colorFondo, colorTexto) = when (curso.estado) {
                                EstadoCurso.COMPLETADO -> Pair(VerdeFondo.copy(alpha = 0.12f), VerdeLetras)
                                EstadoCurso.EN_CURSO -> Pair(Dorado.copy(alpha = 0.18f), Dorado)
                                EstadoCurso.SIN_INICIAR -> Pair(GrisFondoSuave, NegroLetra.copy(alpha = 0.6f))
                            }

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = colorFondo
                            ) {
                                Text(
                                    text = curso.estado.etiqueta,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorTexto,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        // Módulos
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${curso.modulosTotales} módulos",
                                fontSize = 13.sp,
                                color = NegroLetra.copy(alpha = 0.65f)
                            )
                            Text(
                                text = "${curso.modulosCompletados} de ${curso.modulosTotales} completados",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = NegroLetra.copy(alpha = 0.7f)
                            )
                        }

                        // Barra de progreso dorada
                        LinearProgressIndicator(
                            progress = { curso.progreso },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Dorado,
                            trackColor = GrisFondoSuave
                        )
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
