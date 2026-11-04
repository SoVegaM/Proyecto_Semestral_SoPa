package com.example.app_gruposopa.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.BarraNavegacionInferior
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.domain.model.Evento
import com.example.app_gruposopa.ui.viewmodels.EventosViewModel

// Vista 7 - Eventos y contacto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(
    viewModel: EventosViewModel = viewModel(),
    onNavigateTab: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val inscritos by viewModel.inscritos.collectAsState()

    Scaffold(
        containerColor = GrisFondoSuave,
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
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Demostraciones y Talleres", color = VerdeLetras, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text("Participa en nuestras clases en vivo presenciales y online.", style = MaterialTheme.typography.bodyMedium, color = NegroLetra.suave(0.8f))
                    }
                }

                viewModel.eventos.forEach { evento ->
                    val inscrito = evento.id in inscritos
                    TarjetaEvento(
                        evento = evento,
                        inscrito = inscrito,
                        onClick = {
                            viewModel.alternarInscripcion(evento.id)
                            val aviso = if (inscrito) "Inscripción cancelada" else "Inscrito con éxito a ${evento.titulo}"
                            Toast.makeText(context, aviso, Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                // Asesoría por WhatsApp
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
                        Text("Contacta directamente con uno de nuestros técnicos especializados.", style = MaterialTheme.typography.bodySmall, color = NegroLetra.suave(0.7f))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/56912345678?text=Hola,%20necesito%20asesoria"))
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 48.dp)
                        ) {
                            Text("Contactar por WhatsApp", color = Blanco, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaEvento(evento: Evento, inscrito: Boolean, onClick: () -> Unit) {
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
            Text(evento.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NegroLetra)
            Text(evento.detalle, style = MaterialTheme.typography.bodySmall, color = NegroLetra.suave(0.6f))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (inscrito) DoradoTexto else VerdeFondo
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
            ) {
                Text(
                    text = if (inscrito) "✓ Inscrito" else "Inscribirme",
                    color = Blanco,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
