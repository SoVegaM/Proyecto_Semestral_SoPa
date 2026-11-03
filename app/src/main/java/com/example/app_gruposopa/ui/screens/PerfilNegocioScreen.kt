package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PerfilNegocioScreen(
    onGuardarYContinuar: () -> Unit
) {
    var tipoNegocioSeleccionado by remember { mutableStateOf("Panadería") }
    var tamanoSeleccionado by remember { mutableStateOf("Mediano") }
    var interesesSeleccionados by remember { mutableStateOf(setOf("Recetas", "Videos")) }

    val tiposNegocio = listOf("Panadería", "Pastelería", "Chocolatería", "Restaurante")
    val tamanos = listOf("Pequeño", "Mediano", "Grande")
    val intereses = listOf("Recetas", "Videos", "Cursos", "Eventos")

    Scaffold(
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
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
                        color = NegroLetra.copy(alpha = 0.8f)
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
                    Text("Tipo de negocio", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VerdeLetras)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        tiposNegocio.forEach { tipo ->
                            FilterChip(
                                selected = tipoNegocioSeleccionado == tipo,
                                onClick = { tipoNegocioSeleccionado = tipo },
                                label = { Text(tipo) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VerdeFondo,
                                    selectedLabelColor = Blanco
                                )
                            )
                        }
                    }

                    HorizontalDivider(color = GrisFondoSuave)

                    Text("Tamaño aproximado", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VerdeLetras)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        tamanos.forEach { tamano ->
                            FilterChip(
                                selected = tamanoSeleccionado == tamano,
                                onClick = { tamanoSeleccionado = tamano },
                                label = { Text(tamano) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VerdeFondo,
                                    selectedLabelColor = Blanco
                                )
                            )
                        }
                    }

                    HorizontalDivider(color = GrisFondoSuave)

                    Text("Te interesa", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VerdeLetras)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        intereses.forEach { interes ->
                            val selected = interesesSeleccionados.contains(interes)
                            FilterChip(
                                selected = selected,
                                onClick = {
                                    interesesSeleccionados = if (selected) interesesSeleccionados - interes else interesesSeleccionados + interes
                                },
                                label = { Text(interes) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = VerdeFondo,
                                    selectedLabelColor = Blanco
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onGuardarYContinuar,
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Guardar y continuar", color = Blanco, fontWeight = FontWeight.Bold)
            }
        }
    }
}