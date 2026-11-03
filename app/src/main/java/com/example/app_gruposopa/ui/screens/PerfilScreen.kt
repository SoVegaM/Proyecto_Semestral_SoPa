package com.example.app_gruposopa.ui.screens

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.components.BarraNavegacionInferior
import com.example.app_gruposopa.ui.components.IconoValidacion
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.PerfilViewModel
import java.io.File

// Vista 8 - Perfil
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = viewModel(),
    onMisGuardadosClick: () -> Unit = {},
    onAccesibilidadClick: () -> Unit = {},
    onCerrarSesionClick: () -> Unit = {},
    onNavigateTab: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val nombre by viewModel.nombre.collectAsState()
    val detalleNegocio by viewModel.detalleNegocio.collectAsState()
    val fotoPerfil by viewModel.fotoPerfil.collectAsState()
    val editando by viewModel.editando.collectAsState()
    val errorFoto by viewModel.errorFoto.collectAsState()

    // Recurso nativo: abre la galería del celular
    val galeria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let(viewModel::cambiarFoto)
    }

    LaunchedEffect(errorFoto) {
        errorFoto?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.errorFotoMostrado()
        }
    }

    Scaffold(
        containerColor = GrisFondoSuave,
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
                                .background(DoradoTexto)
                                .clickable(onClickLabel = "Cambiar foto de perfil") { galeria.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            val foto = fotoPerfil
                            if (foto != null) {
                                AsyncImage(
                                    model = File(foto),
                                    contentDescription = "Foto de perfil",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(iniciales(nombre), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Blanco)
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = NegroLetra)
                            Text(detalleNegocio, style = MaterialTheme.typography.bodySmall, color = NegroLetra.suave(0.7f))
                        }
                        IconButton(onClick = viewModel::abrirEdicion) {
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
                            onClick = viewModel::abrirEdicion
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
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .heightIn(min = 48.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    if (editando) {
        DialogoEditarPerfil(
            viewModel = viewModel,
            onCambiarFoto = { galeria.launch("image/*") }
        )
    }
}

@Composable
private fun DialogoEditarPerfil(viewModel: PerfilViewModel, onCambiarFoto: () -> Unit) {
    val nombre by viewModel.nombreEditado.collectAsState()
    val errorNombre by viewModel.errorNombre.collectAsState()
    val detalle by viewModel.detalleEditado.collectAsState()
    val errorDetalle by viewModel.errorDetalle.collectAsState()

    AlertDialog(
        onDismissRequest = viewModel::cerrarEdicion,
        title = { Text("Editar Perfil", fontWeight = FontWeight.Bold, color = VerdeLetras) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = viewModel::onNombreChange,
                    label = { Text("Nombre completo") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = VerdeLetras) },
                    trailingIcon = { IconoValidacion(errorNombre, nombre) },
                    isError = errorNombre != null,
                    supportingText = { errorNombre?.let { Text(it) } },
                    singleLine = true
                )
                OutlinedTextField(
                    value = detalle,
                    onValueChange = viewModel::onDetalleChange,
                    label = { Text("Tipo y tamaño de negocio") },
                    leadingIcon = { Icon(Icons.Default.Storefront, contentDescription = null, tint = VerdeLetras) },
                    trailingIcon = { IconoValidacion(errorDetalle, detalle) },
                    isError = errorDetalle != null,
                    supportingText = { errorDetalle?.let { Text(it) } },
                    singleLine = true
                )
                Button(
                    onClick = onCambiarFoto,
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp)
                ) {
                    Icon(Icons.Default.PhotoCamera, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cambiar foto de perfil")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = viewModel::guardarEdicion,
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = viewModel::cerrarEdicion) {
                Text("Cancelar")
            }
        }
    )
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
            .heightIn(min = 48.dp)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = VerdeLetras, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(titulo, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = NegroLetra)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = NegroLetra.suave(0.4f))
    }
}

// "Ana Pérez" queda "AP"
private fun iniciales(nombre: String): String =
    nombre.trim().split(" ").filter { it.isNotBlank() }.take(2)
        .joinToString("") { it.first().uppercase() }
