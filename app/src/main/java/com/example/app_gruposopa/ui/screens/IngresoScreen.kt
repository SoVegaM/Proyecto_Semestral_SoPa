package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_gruposopa.ui.components.IconoValidacion
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.IngresoViewModel

// Vista 2 - Ingreso y registro
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngresoScreen(
    viewModel: IngresoViewModel = viewModel(),
    onIngresoExitoso: (String) -> Unit // Recibe el nombre del usuario
) {
    val esRegistro by viewModel.esRegistro.collectAsState()
    val nombre by viewModel.nombre.collectAsState()
    val errorNombre by viewModel.errorNombre.collectAsState()
    val correo by viewModel.correo.collectAsState()
    val errorCorreo by viewModel.errorCorreo.collectAsState()
    val clave by viewModel.clave.collectAsState()
    val errorClave by viewModel.errorClave.collectAsState()

    Scaffold(
        containerColor = GrisFondoSuave,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (esRegistro) "Crear Cuenta" else "Ingreso",
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
                .fillMaxSize()
                .imePadding(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 640.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TabRow(
                            selectedTabIndex = if (esRegistro) 1 else 0,
                            containerColor = Blanco,
                            contentColor = VerdeLetras
                        ) {
                            Tab(
                                selected = !esRegistro,
                                onClick = { viewModel.cambiarModo(registro = false) },
                                text = { Text("Iniciar Sesión", fontWeight = FontWeight.Bold) }
                            )
                            Tab(
                                selected = esRegistro,
                                onClick = { viewModel.cambiarModo(registro = true) },
                                text = { Text("Crear Cuenta", fontWeight = FontWeight.Bold) }
                            )
                        }

                        if (esRegistro) {
                            OutlinedTextField(
                                value = nombre,
                                onValueChange = viewModel::onNombreChange,
                                label = { Text("Nombre completo") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = VerdeLetras) },
                                trailingIcon = { IconoValidacion(errorNombre, nombre) },
                                isError = errorNombre != null,
                                supportingText = { errorNombre?.let { Text(it) } },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VerdeFondo, focusedLabelColor = VerdeLetras),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        OutlinedTextField(
                            value = correo,
                            onValueChange = viewModel::onCorreoChange,
                            label = { Text("Correo electrónico") },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = VerdeLetras) },
                            trailingIcon = { IconoValidacion(errorCorreo, correo) },
                            isError = errorCorreo != null,
                            supportingText = { errorCorreo?.let { Text(it) } },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VerdeFondo, focusedLabelColor = VerdeLetras),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = clave,
                            onValueChange = viewModel::onClaveChange,
                            label = { Text("Contraseña") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = VerdeLetras) },
                            trailingIcon = { IconoValidacion(errorClave, clave) },
                            isError = errorClave != null,
                            supportingText = { errorClave?.let { Text(it) } },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VerdeFondo, focusedLabelColor = VerdeLetras),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = { viewModel.enviar()?.let(onIngresoExitoso) },
                            colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 48.dp)
                        ) {
                            Text(
                                text = if (esRegistro) "Registrar y Continuar" else "Ingresar",
                                color = Blanco,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
