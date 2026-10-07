package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gruposopa.ui.theme.*

/**
 * Vista 5 · Detalle de contenido (Video e insumo)
 * Maqueta para celular compacto según el wireframe oficial:
 * - Barra superior: ← Detalle
 * - Reproductor de video (mockup oscuro con botón Play)
 * - Categoría y duración
 * - Título y descripción técnica del proceso
 * - Tarjeta "Insumo destacado"
 * - Botones inferiores: "Guardar" y "Consultar" (WhatsApp/asesoría)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    modifier: Modifier = Modifier,
    onVolverClick: () -> Unit = {},
    onConsultarClick: () -> Unit = {}
) {
    var guardado by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalle",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Blanco
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdeFondo
                )
            )
        },
        bottomBar = {
            // Barra inferior fija con botones de acción (mínimo 48dp de altura para accesibilidad)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                color = Blanco,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botón Guardar / Quitar
                    OutlinedButton(
                        onClick = { guardado = !guardado },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (guardado) VerdeFondo.copy(alpha = 0.08f) else Blanco
                        )
                    ) {
                        Icon(
                            imageVector = if (guardado) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = if (guardado) VerdeLetras else NegroLetra,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (guardado) "Guardado" else "Guardar",
                            color = if (guardado) VerdeLetras else NegroLetra,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Botón Consultar (Asesoría / WhatsApp)
                    Button(
                        onClick = onConsultarClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VerdeFondo,
                            contentColor = Blanco
                        )
                    ) {
                        Text(
                            text = "Consultar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
                .verticalScroll(scrollState)
        ) {
            // 1. Contenedor del reproductor de video (mockup visual 16:9 con Play central)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .background(Color(0xFF1B2422))
                    .clickable { /* Simulación de reproducir video */ },
                contentAlignment = Alignment.Center
            ) {
                // Botón Play
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape,
                    color = Blanco.copy(alpha = 0.25f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Reproducir video",
                            tint = Blanco,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                // Badge de duración sobre el video
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Black.copy(alpha = 0.7f)
                ) {
                    Text(
                        text = "45:00",
                        color = Blanco,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // 2. Información del contenido
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Categoría y duración
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Panadería y pastelería",
                        color = VerdeLetras,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "  ·  45 min",
                        color = NegroLetra.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }

                // Título
                Text(
                    text = "Laminado y horneado de croissants",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = NegroLetra,
                    lineHeight = 26.sp
                )

                // Descripción técnica
                Text(
                    text = "Empastado, vueltas simples y dobles, fermentación y horneado. Aprende los pasos fundamentales para conseguir un hojaldre crujiente y aireado con metodología profesional Master Martini.",
                    fontSize = 14.sp,
                    color = NegroLetra.copy(alpha = 0.85f),
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 3. Tarjeta de Insumo Destacado
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Blanco),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = Dorado.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Insumo destacado",
                                color = Dorado,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = "Margarinas y mantecas especiales Máster",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = VerdeLetras
                        )

                        Text(
                            text = "Grasas vegetales de alto punto de fusión que garantizan laminados definidos y excelente plasticidad.",
                            fontSize = 13.sp,
                            color = NegroLetra.copy(alpha = 0.75f),
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Vista 5 - Detalle Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun DetalleScreenPreview() {
    App_GrupoSoPaTheme {
        DetalleScreen()
    }
}
