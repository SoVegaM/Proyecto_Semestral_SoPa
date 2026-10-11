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
import com.example.app_gruposopa.ui.theme.*

/**
 * Modelo de datos de presentación para los elementos guardados.
 */
data class ContenidoGuardado(
    val id: Int,
    val titulo: String,
    val tipo: String, // "Video" o "Receta"
    val duracion: String
)

/**
 * Vista 10 · Mis guardados (Favoritos)
 * Maqueta para celular compacto según el wireframe oficial:
 * - Barra superior: ← Mis guardados (#004D42)
 * - Filtros rápidos: Todos / Recetas / Videos
 * - Lista de tarjetas con título, tipo, duración e ícono para quitar guardado
 * - Estado vacío con mensaje claro ("Aún no guardas contenidos")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuardadosScreen(
    onVolverClick: () -> Unit = {},
    onItemClick: (ContenidoGuardado) -> Unit = {},
    onVideoClick: ((String) -> Unit)? = null
) {
    val repository = remember { com.example.app_gruposopa.data.repository.ContenidosRepository.instance }
    val guardadosIds by repository.guardados.collectAsState()

    // Filtro seleccionado
    var filtroSeleccionado by remember { mutableStateOf("Todos") }
    val filtros = listOf("Todos", "Recetas", "Videos")

    // Elementos guardados reales desde el repositorio en memoria
    val videosReales = remember(guardadosIds) {
        repository.obtenerVideosPorIds(guardadosIds.toList())
    }

    // Si hay videos reales guardados, los adaptamos; si no, mostramos ejemplos base del wireframe
    var listaWireframe by remember {
        mutableStateOf<List<ContenidoGuardado>>(
            listOf(
                ContenidoGuardado(1, "Leche Asada", "Video", "1:16"),
                ContenidoGuardado(2, "Mousse de Frutilla", "Receta", "0:38"),
                ContenidoGuardado(3, "Torta Chocolate Avellana", "Video", "1:17")
            )
        )
    }

    // Si hay videos reales guardados en el repositorio, los usamos prioritariamente
    val listaGuardados = remember(videosReales, listaWireframe) {
        if (videosReales.isNotEmpty()) {
            videosReales.map { v ->
                ContenidoGuardado(
                    id = v.id.replace("v", "").toIntOrNull() ?: 1,
                    titulo = v.titulo,
                    tipo = "Video",
                    duracion = v.duracion
                )
            }
        } else {
            listaWireframe
        }
    }

    val guardadosFiltrados = remember(filtroSeleccionado, listaGuardados) {
        when (filtroSeleccionado) {
            "Recetas" -> listaGuardados.filter { it.tipo.equals("Receta", ignoreCase = true) }
            "Videos" -> listaGuardados.filter { it.tipo.equals("Video", ignoreCase = true) }
            else -> listaGuardados
        }
    }

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
                filtros.forEach { filtro ->
                    val esSeleccionado = (filtro == filtroSeleccionado)
                    FilterChip(
                        selected = esSeleccionado,
                        onClick = { filtroSeleccionado = filtro },
                        label = {
                            Text(
                                text = filtro,
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
                                "No tienes contenidos guardados en la categoría \"$filtroSeleccionado\"."
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
                            onClick = {
                                val videoReal = videosReales.find { it.titulo == item.titulo }
                                if (videoReal != null && onVideoClick != null) {
                                    onVideoClick(videoReal.id)
                                } else {
                                    onItemClick(item)
                                }
                            }
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
                                        text = "${item.tipo} · ${item.duracion}",
                                        fontSize = 12.sp,
                                        color = NegroLetra.copy(alpha = 0.6f)
                                    )
                                }

                                // Botón para quitar de guardados (accesible >= 48dp)
                                IconButton(
                                    onClick = {
                                        val videoReal = videosReales.find { it.titulo == item.titulo }
                                        if (videoReal != null) {
                                            repository.toggleGuardado(videoReal.id)
                                        } else {
                                            listaWireframe = listaWireframe.filter { it.id != item.id }
                                        }
                                    },
                                    modifier = Modifier.size(48.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Quitar de guardados",
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
