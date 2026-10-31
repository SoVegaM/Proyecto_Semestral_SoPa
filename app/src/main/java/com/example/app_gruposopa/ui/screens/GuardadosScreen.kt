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
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.utils.esPantallaAncha
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
    val notas by viewModel.notas.collectAsState()
    val ancha = esPantallaAncha()
    // Guardado al que se le está escribiendo la nota (null = cuadro cerrado)
    var editandoNota by remember { mutableStateOf<ContenidoMasterMartini?>(null) }

    editandoNota?.let { item ->
        DialogoNota(
            titulo = item.titulo,
            notaActual = notas[item.id].orEmpty(),
            onGuardar = { texto ->
                viewModel.guardarNota(item.id, texto)
                editandoNota = null
            },
            onCancelar = { editandoNota = null }
        )
    }

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
                    // En pantalla ancha, dos tarjetas por fila
                    guardadosFiltrados.chunked(if (ancha) 2 else 1).forEach { fila ->
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            fila.forEach { item ->
                                Box(Modifier.weight(1f)) {
                                    TarjetaGuardado(
                                        item = item,
                                        nota = notas[item.id],
                                        onAbrir = { onVideoClick(item.id) },
                                        onEditarNota = { editandoNota = item },
                                        onQuitar = { viewModel.quitarGuardado(item.id) }
                                    )
                                }
                            }
                            // Si la última fila queda con una sola tarjeta, se rellena el espacio
                            if (fila.size == 1 && ancha) Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

// Tarjeta de un guardado: título, tipo, nota y botones de nota y quitar
@Composable
private fun TarjetaGuardado(
    item: ContenidoMasterMartini,
    nota: String?,
    onAbrir: () -> Unit,
    onEditarNota: () -> Unit,
    onQuitar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onAbrir
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
                nota?.let { nota ->
                    Text(
                        text = "Nota: $nota",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = VerdeLetras
                    )
                }
            }

            // Escribir o editar la nota
            IconButton(
                onClick = onEditarNota,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.EditNote,
                    contentDescription = if (nota == null) "Agregar nota a ${item.titulo}" else "Editar nota de ${item.titulo}",
                    tint = VerdeLetras,
                    modifier = Modifier.size(26.dp)
                )
            }

            // Quitar (mínimo 48dp)
            IconButton(
                onClick = onQuitar,
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

// Cuadro para escribir la nota de un guardado
@Composable
private fun DialogoNota(
    titulo: String,
    notaActual: String,
    onGuardar: (String) -> Unit,
    onCancelar: () -> Unit
) {
    // El cursor parte al final, para seguir escribiendo después de lo que ya había
    var texto by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(notaActual, TextRange(notaActual.length)))
    }
    AlertDialog(
        onDismissRequest = onCancelar,
        title = {
            Text(
                text = "Nota para $titulo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VerdeLetras
            )
        },
        text = {
            OutlinedTextField(
                value = texto,
                onValueChange = { if (it.text.length <= GuardadosViewModel.LARGO_MAXIMO_NOTA) texto = it },
                placeholder = { Text("Ej: probar con menos azúcar") },
                supportingText = { Text("${texto.text.length}/${GuardadosViewModel.LARGO_MAXIMO_NOTA}") },
                minLines = 3,
                maxLines = 5,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { onGuardar(texto.text) },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo, contentColor = Blanco)
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar", color = VerdeLetras) }
        },
        containerColor = Blanco
    )
}

@Preview(name = "Vista 10 - Guardados Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun GuardadosScreenPreview() {
    App_GrupoSoPaTheme {
        GuardadosScreen()
    }
}
