package com.example.app_gruposopa.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.BarraNavegacionInferior
import com.example.app_gruposopa.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onMisGuardadosClick: () -> Unit = {},
    onEditarPerfilClick: () -> Unit = {},
    onAccesibilidadClick: () -> Unit = {},
    onCerrarSesionClick: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    var nombreUsuario by remember { mutableStateOf("Carla Fuentes") }
    var detalleNegocio by remember { mutableStateOf("Panadería · Mediano") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var mostrarDialogoEditar by remember { mutableStateOf(false) }

    // Launcher Nativo para seleccionar foto de perfil de la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { fotoUri = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi Perfil",
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
                rutaActual = AppScreen.Perfil.route,
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
            // Cabecera Usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Dorado)
                            .clickable { galleryLauncher.launch("image/*") },
                        contentAlignment = Alignment.Center
                    ) {
                        if (fotoUri != null) {
                            AsyncImage(
                                model = fotoUri,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text("CF", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Blanco)
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(nombreUsuario, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NegroLetra)
                        Text(detalleNegocio, style = MaterialTheme.typography.bodySmall, color = NegroLetra.copy(alpha = 0.7f))
                    }
                    IconButton(onClick = { mostrarDialogoEditar = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar Perfil", tint = VerdeLetras)
                    }
                }
            }

            // Opciones de configuración
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    OpcionPerfilItem(
                        icon = Icons.Default.Bookmark,
                        titulo = "Mis guardados",
                        onClick = onMisGuardadosClick
                    )
                    HorizontalDivider(color = GrisFondoSuave)
                    OpcionPerfilItem(
                        icon = Icons.Default.Edit,
                        titulo = "Editar perfil",
                        onClick = { mostrarDialogoEditar = true }
                    )
                    HorizontalDivider(color = GrisFondoSuave)
                    OpcionPerfilItem(
                        icon = Icons.Default.AccessibilityNew,
                        titulo = "Accesibilidad",
                        onClick = onAccesibilidadClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onCerrarSesionClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Cerrar sesión", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }
        }
    }

    // Diálogo para Editar Perfil
    if (mostrarDialogoEditar) {
        var tempNombre by remember { mutableStateOf(nombreUsuario) }
        var tempDetalle by remember { mutableStateOf(detalleNegocio) }

        AlertDialog(
            onDismissRequest = { mostrarDialogoEditar = false },
            title = { Text("Editar Perfil", fontWeight = FontWeight.Bold, color = VerdeLetras) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = tempNombre,
                        onValueChange = { tempNombre = it },
                        label = { Text("Nombre completo") }
                    )
                    OutlinedTextField(
                        value = tempDetalle,
                        onValueChange = { tempDetalle = it },
                        label = { Text("Tipo y tamaño de negocio") }
                    )
                    Button(
                        onClick = { galleryLauncher.launch("image/*") },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cambiar foto de perfil")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        nombreUsuario = tempNombre
                        detalleNegocio = tempDetalle
                        mostrarDialogoEditar = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo)
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEditar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun OpcionPerfilItem(
    icon: ImageVector,
    titulo: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = VerdeLetras, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(titulo, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = NegroLetra)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = NegroLetra.copy(alpha = 0.4f))
    }
}