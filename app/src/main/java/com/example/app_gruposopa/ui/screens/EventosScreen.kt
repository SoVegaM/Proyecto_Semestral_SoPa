package com.example.app_gruposopa.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.BarraNavegacionInferior
import com.example.app_gruposopa.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(
    onNavigateTab: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var eventoInscrito by remember { mutableStateOf<String?>(null) }
    val inscritos = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Eventos y Contacto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeFondo)
            )
        },
        bottomBar = {
            BarraNavegacionInferior(
                rutaActual = AppScreen.Eventos.route,
                onNavegar = onNavigateTab
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Demostraciones y Talleres", color = VerdeLetras, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("Participa en nuestras clases en vivo presenciales y online.", style = MaterialTheme.typography.bodyMedium, color = NegroLetra.copy(alpha = 0.8f))
                }
            }

            // Tarjeta Evento 1
            val id1 = "taller_templado"
            val isInscrito1 = inscritos[id1] == true
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Taller de templado de chocolate", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NegroLetra)
                    Text("15 oct · Presencial · Centro Técnico", style = MaterialTheme.typography.bodySmall, color = NegroLetra.copy(alpha = 0.6f))

                    Button(
                        onClick = {
                            if (!isInscrito1) {
                                inscritos[id1] = true
                                Toast.makeText(context, "Inscrito con éxito a Taller de templado", Toast.LENGTH_SHORT).show()
                            } else {
                                inscritos[id1] = false
                                Toast.makeText(context, "Inscripción cancelada", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInscrito1) Dorado else VerdeFondo
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isInscrito1) "✓ Inscrito" else "Inscribirme",
                            color = Blanco,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Tarjeta Evento 2
            val id2 = "demo_heladeria"
            val isInscrito2 = inscritos[id2] == true
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Demo de heladería artesanal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NegroLetra)
                    Text("28 oct · Online vía Zoom", style = MaterialTheme.typography.bodySmall, color = NegroLetra.copy(alpha = 0.6f))

                    Button(
                        onClick = {
                            if (!isInscrito2) {
                                inscritos[id2] = true
                                Toast.makeText(context, "Inscrito con éxito a Demo de heladería", Toast.LENGTH_SHORT).show()
                            } else {
                                inscritos[id2] = false
                                Toast.makeText(context, "Inscripción cancelada", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInscrito2) Dorado else VerdeFondo
                        ),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isInscrito2) "✓ Inscrito" else "Inscribirme",
                            color = Blanco,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Asesoría por WhatsApp (Recurso Nativo 1)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("¿Necesitas asesoría técnica?", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = VerdeLetras)
                    Text("Contacta directamente con uno de nuestros técnicos especializados.", style = MaterialTheme.typography.bodySmall, color = NegroLetra.copy(alpha = 0.7f))

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://wa.me/56912345678?text=Hola,%20necesito%20asesoria")
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Contactar por WhatsApp", color = Blanco, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}