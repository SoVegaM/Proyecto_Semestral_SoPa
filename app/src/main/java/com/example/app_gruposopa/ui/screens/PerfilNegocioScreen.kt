package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.domain.model.OpcionesDeNegocio
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.PerfilNegocioViewModel

// Vista 3 - Perfil de negocio
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilNegocioScreen(
    viewModel: PerfilNegocioViewModel = viewModel(),
    onGuardarYContinuar: () -> Unit
) {
    val tipo by viewModel.tipo.collectAsState()
    val tamano by viewModel.tamano.collectAsState()
    val intereses by viewModel.intereses.collectAsState()
    val errorIntereses by viewModel.errorIntereses.collectAsState()

    Scaffold(
        containerColor = GrisFondoSuave,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil de negocio",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeFondo)
            )
        }
    ) { innerPadding ->
        // En tablet el contenido no pasa de 640dp de ancho y queda centrado
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 640.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
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
                            text = "Cuéntanos de ti",
                            color = VerdeLetras,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Personalizaremos las recomendaciones de insumos y cursos para tu negocio.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NegroLetra.suave(0.8f)
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TituloSeccion(Icons.Default.Storefront, "Tipo de negocio")
                        GrupoOpciones(
                            opciones = OpcionesDeNegocio.tipos,
                            estaElegida = { it == tipo },
                            onClick = viewModel::seleccionarTipo
                        )

                        HorizontalDivider(color = GrisFondoSuave)

                        TituloSeccion(Icons.Default.Groups, "Tamaño aproximado")
                        GrupoOpciones(
                            opciones = OpcionesDeNegocio.tamanos,
                            estaElegida = { it == tamano },
                            onClick = viewModel::seleccionarTamano
                        )

                        HorizontalDivider(color = GrisFondoSuave)

                        TituloSeccion(Icons.Default.Favorite, "Te interesa")
                        GrupoOpciones(
                            opciones = OpcionesDeNegocio.intereses,
                            estaElegida = { it in intereses },
                            onClick = viewModel::alternarInteres
                        )
                        errorIntereses?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { viewModel.guardar(onGuardarYContinuar) },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                ) {
                    Text("Guardar y continuar", color = Blanco, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun TituloSeccion(icono: ImageVector, texto: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icono, contentDescription = null, tint = VerdeLetras, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VerdeLetras)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GrupoOpciones(
    opciones: List<String>,
    estaElegida: (String) -> Boolean,
    onClick: (String) -> Unit
) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        opciones.forEach { opcion ->
            val elegida = estaElegida(opcion)
            FilterChip(
                selected = elegida,
                onClick = { onClick(opcion) },
                label = { Text(opcion) },
                leadingIcon = if (elegida) {
                    { Icon(Icons.Default.Check, contentDescription = null, tint = Blanco, modifier = Modifier.size(18.dp)) }
                } else {
                    null
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = VerdeFondo,
                    selectedLabelColor = Blanco
                )
            )
        }
    }
}
