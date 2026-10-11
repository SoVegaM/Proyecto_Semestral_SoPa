package com.example.app_gruposopa.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.theme.*

/**
 * Pantallas complementarias interactivas según el mapa de navegación del MVP:
 * - Vista 2: Ingreso (Login o invitado)
 * - Vista 3: Perfil de negocio (Segmentación)
 * - Vista 7: Eventos y contacto (Asesoría)
 * - Vista 8: Perfil (Mis datos, accesos a Guardados y Accesibilidad)
 * - Vista 9: Accesibilidad (Texto, contraste)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderVistaScreen(
    pantallaActual: AppScreen,
    navController: NavController? = null,
    onVerHomeScreenAdaptable: (() -> Unit)? = null
) {
    val context = LocalContext.current

    when (pantallaActual) {
        AppScreen.Ingreso -> {
            IngresoView(
                onLogin = { navController?.navigate(AppScreen.PerfilNegocio.route) },
                onInvitado = { navController?.navigate(AppScreen.Inicio.route) }
            )
        }
        AppScreen.PerfilNegocio -> {
            PerfilNegocioView(
                onContinuar = { navController?.navigate(AppScreen.Inicio.route) }
            )
        }
        AppScreen.Eventos -> {
            EventosView(
                onNavigateTab = { ruta -> navController?.navigate(ruta) },
                onContactar = {
                    Toast.makeText(context, "Conectando con asesor técnico Master Martini...", Toast.LENGTH_SHORT).show()
                }
            )
        }
        AppScreen.Perfil -> {
            PerfilView(
                onNavigateTab = { ruta -> navController?.navigate(ruta) },
                onIrGuardados = { navController?.navigate(AppScreen.Guardados.route) },
                onIrAccesibilidad = { navController?.navigate(AppScreen.Accesibilidad.route) },
                onCerrarSesion = {
                    navController?.navigate(AppScreen.Bienvenida.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        AppScreen.Accesibilidad -> {
            AccesibilidadView(
                onVolver = { navController?.popBackStack() }
            )
        }
        else -> {
            // Vista genérica de respaldo
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrisFondoSuave),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = pantallaActual.nombre,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeLetras,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = pantallaActual.descripcion,
                        fontSize = 14.sp,
                        color = NegroLetra.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { navController?.navigate(AppScreen.Inicio.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo)
                    ) {
                        Text("Ir al Inicio", color = Blanco)
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VISTA 2 · INGRESO Y REGISTRO
// ---------------------------------------------------------------------------
@Composable
private fun IngresoView(
    onLogin: () -> Unit,
    onInvitado: () -> Unit
) {
    var email by remember { mutableStateOf("chef@pasteleria.cl") }
    var password by remember { mutableStateOf("••••••••") }

    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = VerdeFondo) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ingreso a Master Martini", color = Blanco, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Blanco)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("¡Bienvenido!", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = VerdeLetras)
            Text(
                "Accede a cursos certificados y asesoría técnica.",
                fontSize = 13.sp,
                color = NegroLetra.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Iniciar sesión", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Blanco)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onInvitado,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Continuar como invitado", fontSize = 14.sp, color = VerdeLetras)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VISTA 3 · PERFIL DE NEGOCIO
// ---------------------------------------------------------------------------
@Composable
private fun PerfilNegocioView(
    onContinuar: () -> Unit
) {
    val rubros = listOf("Pastelería", "Panadería", "Chocolatería", "Gelatería / Heladería", "Pizzería", "Restaurante")
    var rubroSeleccionado by remember { mutableStateOf("Pastelería") }

    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = VerdeFondo) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Perfil de tu Negocio", color = Blanco, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Cuéntanos sobre tu emprendimiento", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = VerdeLetras)
            Text(
                "Personalizaremos los videos técnicos, cursos y recomendaciones según tu especialidad gastronómica.",
                fontSize = 13.sp,
                color = NegroLetra.copy(alpha = 0.75f)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Selecciona tu rubro principal:", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = NegroLetra)
                    rubros.forEach { rubro ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { rubroSeleccionado = rubro }
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = rubroSeleccionado == rubro,
                                onClick = { rubroSeleccionado = rubro },
                                colors = RadioButtonDefaults.colors(selectedColor = VerdeFondo)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(rubro, fontSize = 14.sp, color = NegroLetra)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onContinuar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Continuar al Centro Técnico →", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = NegroLetra)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VISTA 7 · EVENTOS Y CONTACTO
// ---------------------------------------------------------------------------
@Composable
private fun EventosView(
    onNavigateTab: (String) -> Unit,
    onContactar: () -> Unit
) {
    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = VerdeFondo) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Eventos & Asesoría Técnica", color = Blanco, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(tabActual = "eventos", onNavigateTab = onNavigateTab)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Masterclass Chocolatería Caravella", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = VerdeLetras)
                    Text("Técnicas de templado y bombones de autor con Chef Lili Cañete.", fontSize = 13.sp, color = NegroLetra.copy(alpha = 0.75f))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("📅 Próximo Jueves", fontSize = 12.sp, color = Dorado, fontWeight = FontWeight.Bold)
                        Text("📍 Centro Técnico Santiago", fontSize = 12.sp, color = NegroLetra.copy(alpha = 0.6f))
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Demostración Heladería Martini Gelato", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = VerdeLetras)
                    Text("Formulación y texturizado de gelato tradicional y sorbetes veganos.", fontSize = 13.sp, color = NegroLetra.copy(alpha = 0.75f))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("📅 En 2 semanas", fontSize = 12.sp, color = Dorado, fontWeight = FontWeight.Bold)
                        Text("📍 Modalidad Híbrida", fontSize = 12.sp, color = NegroLetra.copy(alpha = 0.6f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onContactar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Solicitar asesoría técnica por WhatsApp", fontWeight = FontWeight.Bold, color = Blanco)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VISTA 8 · PERFIL
// ---------------------------------------------------------------------------
@Composable
private fun PerfilView(
    onNavigateTab: (String) -> Unit,
    onIrGuardados: () -> Unit,
    onIrAccesibilidad: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), color = VerdeFondo) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Mi Perfil", color = Blanco, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(tabActual = "perfil", onNavigateTab = onNavigateTab)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Tarjeta de usuario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(54.dp),
                        shape = RoundedCornerShape(27.dp),
                        color = VerdeFondo.copy(alpha = 0.12f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = VerdeLetras, modifier = Modifier.size(30.dp))
                        }
                    }
                    Column {
                        Text("Chef Profesional", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = NegroLetra)
                        Text("Pastelería & Panadería Fina", fontSize = 13.sp, color = NegroLetra.copy(alpha = 0.65f))
                    }
                }
            }

            // Opción 1: Mis Guardados -> Vista 10
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onIrGuardados() },
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Outlined.Bookmark, contentDescription = null, tint = Dorado)
                        Text("Mis guardados (Favoritos)", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = NegroLetra)
                    }
                    Text("→", fontSize = 16.sp, color = NegroLetra.copy(alpha = 0.5f))
                }
            }

            // Opción 2: Accesibilidad -> Vista 9
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onIrAccesibilidad() },
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = VerdeLetras)
                        Text("Accesibilidad", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = NegroLetra)
                    }
                    Text("→", fontSize = 16.sp, color = NegroLetra.copy(alpha = 0.5f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Cerrar sesión -> Vista 1 Bienvenida
            OutlinedButton(
                onClick = onCerrarSesion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Cerrar sesión", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VISTA 9 · ACCESIBILIDAD
// ---------------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccesibilidadView(
    onVolver: () -> Unit
) {
    var altoContraste by remember { mutableStateOf(false) }
    var textoGrande by remember { mutableStateOf(false) }
    var reducirAnimaciones by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Accesibilidad", color = Blanco, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Blanco)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeFondo)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Blanco),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Alto contraste", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = NegroLetra)
                            Text("Aumenta la diferenciación de colores de texto y fondo.", fontSize = 12.sp, color = NegroLetra.copy(alpha = 0.6f))
                        }
                        Switch(checked = altoContraste, onCheckedChange = { altoContraste = it })
                    }

                    HorizontalDivider(color = GrisFondoSuave)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Texto aumentado", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = NegroLetra)
                            Text("Aumenta el tamaño de fuentes y títulos en las vistas.", fontSize = 12.sp, color = NegroLetra.copy(alpha = 0.6f))
                        }
                        Switch(checked = textoGrande, onCheckedChange = { textoGrande = it })
                    }

                    HorizontalDivider(color = GrisFondoSuave)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Reducir animaciones", fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = NegroLetra)
                            Text("Desactiva transiciones y animaciones al navegar.", fontSize = 12.sp, color = NegroLetra.copy(alpha = 0.6f))
                        }
                        Switch(checked = reducirAnimaciones, onCheckedChange = { reducirAnimaciones = it })
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onVolver,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Guardar y volver", color = Blanco, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// BARRA DE NAVEGACIÓN INFERIOR REUTILIZABLE
// ---------------------------------------------------------------------------
@Composable
private fun BottomNavigationBar(
    tabActual: String,
    onNavigateTab: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = Blanco,
        shadowElevation = 8.dp
    ) {
        NavigationBar(containerColor = Blanco, tonalElevation = 0.dp) {
            NavigationBarItem(
                selected = tabActual == "inicio",
                onClick = { onNavigateTab("inicio") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                label = { Text("Inicio", fontSize = 11.sp) }
            )
            NavigationBarItem(
                selected = tabActual == "cursos",
                onClick = { onNavigateTab("cursos") },
                icon = { Icon(Icons.Default.School, contentDescription = "Cursos") },
                label = { Text("Cursos", fontSize = 11.sp) }
            )
            NavigationBarItem(
                selected = tabActual == "eventos",
                onClick = { onNavigateTab("eventos") },
                icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Eventos") },
                label = { Text("Eventos", fontSize = 11.sp) }
            )
            NavigationBarItem(
                selected = tabActual == "perfil",
                onClick = { onNavigateTab("perfil") },
                icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
                label = { Text("Perfil", fontSize = 11.sp) }
            )
        }
    }
}
