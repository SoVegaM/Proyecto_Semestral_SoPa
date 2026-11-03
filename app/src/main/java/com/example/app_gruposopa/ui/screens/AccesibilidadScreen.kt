package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.AccesibilidadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccesibilidadScreen(
    viewModel: AccesibilidadViewModel = viewModel(),
    onVolverClick: () -> Unit = {}
) {
    val tamanoGuardado by viewModel.tamanoTexto.collectAsState()
    val contrasteGuardado by viewModel.altoContraste.collectAsState()
    val negritaGuardada by viewModel.textoNegrita.collectAsState()

    var sliderPosition by remember { mutableFloatStateOf(tamanoGuardado) }
    var altoContraste by remember { mutableStateOf(contrasteGuardado) }
    var textoNegrita by remember { mutableStateOf(negritaGuardada) }

    // Sincroniza los estados cuando se carguen los datos desde DataStore
    LaunchedEffect(tamanoGuardado, contrasteGuardado, negritaGuardada) {
        sliderPosition = tamanoGuardado
        altoContraste = contrasteGuardado
        textoNegrita = negritaGuardada
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Accesibilidad",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Blanco)
                    }
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
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Ajustes de visualización", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VerdeLetras)

                    Text("Tamaño del texto", style = MaterialTheme.typography.bodyMedium, color = NegroLetra)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("A", style = MaterialTheme.typography.bodySmall, color = NegroLetra)
                        Slider(
                            value = sliderPosition,
                            onValueChange = { sliderPosition = it },
                            valueRange = 12f..24f,
                            colors = SliderDefaults.colors(
                                thumbColor = Dorado,
                                activeTrackColor = Dorado,
                                inactiveTrackColor = GrisFondoSuave
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Text("A", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = NegroLetra)
                    }

                    HorizontalDivider(color = GrisFondoSuave)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Alto contraste", style = MaterialTheme.typography.bodyMedium, color = NegroLetra)
                        Switch(
                            checked = altoContraste,
                            onCheckedChange = { altoContraste = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Blanco, checkedTrackColor = VerdeFondo)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Texto en negrita", style = MaterialTheme.typography.bodyMedium, color = NegroLetra)
                        Switch(
                            checked = textoNegrita,
                            onCheckedChange = { textoNegrita = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Blanco, checkedTrackColor = VerdeFondo)
                        )
                    }
                }
            }

            // Vista previa en tiempo real
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Vista previa", style = MaterialTheme.typography.labelSmall, color = VerdeLetras, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Laminado y horneado de croissants",
                        fontSize = sliderPosition.sp,
                        fontWeight = if (textoNegrita) FontWeight.Bold else FontWeight.Normal,
                        color = if (altoContraste) NegroLetra else VerdeLetras
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.guardarCambios(sliderPosition, altoContraste, textoNegrita)
                    onVolverClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Guardar cambios", color = Blanco, fontWeight = FontWeight.Bold)
            }
        }
    }
}