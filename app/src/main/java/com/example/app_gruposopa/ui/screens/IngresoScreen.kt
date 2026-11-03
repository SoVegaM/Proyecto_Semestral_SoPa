package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
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
import com.example.app_gruposopa.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngresoScreen(
    onIngresoExitoso: (String) -> Unit // Recibe el nombre del usuario
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Ingresar, 1 = Registrarse

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nombreRegistro by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var nombreError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedTab == 0) "Ingreso" else "Crear Cuenta",
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
                        selectedTabIndex = selectedTab,
                        containerColor = Blanco,
                        contentColor = VerdeLetras
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = { Text("Iniciar Sesión", fontWeight = FontWeight.Bold) }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Crear Cuenta", fontWeight = FontWeight.Bold) }
                        )
                    }

                    if (selectedTab == 1) {
                        OutlinedTextField(
                            value = nombreRegistro,
                            onValueChange = {
                                nombreRegistro = it
                                nombreError = if (it.isNotBlank()) null else "Ingresa tu nombre"
                            },
                            label = { Text("Nombre completo") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = VerdeLetras) },
                            isError = nombreError != null,
                            supportingText = { nombreError?.let { Text(it) } },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VerdeFondo, focusedLabelColor = VerdeLetras),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (it.contains("@")) null else "Correo inválido"
                        },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = VerdeLetras) },
                        isError = emailError != null,
                        supportingText = { emailError?.let { Text(it) } },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VerdeFondo, focusedLabelColor = VerdeLetras),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = if (it.length >= 6) null else "Mínimo 6 caracteres"
                        },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = VerdeLetras) },
                        isError = passwordError != null,
                        supportingText = { passwordError?.let { Text(it) } },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = VerdeFondo, focusedLabelColor = VerdeLetras),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            emailError = if (email.contains("@")) null else "Correo inválido"
                            passwordError = if (password.length >= 6) null else "Mínimo 6 caracteres"
                            if (selectedTab == 1) {
                                nombreError = if (nombreRegistro.isNotBlank()) null else "Ingresa tu nombre"
                            }

                            val esValido = if (selectedTab == 0) {
                                emailError == null && passwordError == null && email.isNotBlank() && password.isNotBlank()
                            } else {
                                emailError == null && passwordError == null && nombreError == null && email.isNotBlank() && password.isNotBlank() && nombreRegistro.isNotBlank()
                            }

                            if (esValido) {
                                // Si es registro usa nombreRegistro; si es login usa la parte antes del @ del correo
                                val nombreEnviar = if (selectedTab == 1) {
                                    nombreRegistro
                                } else {
                                    email.substringBefore("@").replaceFirstChar { it.uppercase() }
                                }
                                onIngresoExitoso(nombreEnviar)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = if (selectedTab == 0) "Ingresar" else "Registrar y Continuar",
                            color = Blanco,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}