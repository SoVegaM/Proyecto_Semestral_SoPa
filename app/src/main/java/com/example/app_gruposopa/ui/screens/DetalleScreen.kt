package com.example.app_gruposopa.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.viewmodels.DetalleViewModel

/**
 * Vista 5 · Detalle de contenido (Video e insumo)
 * Conectada al repositorio central en memoria:
 * - Reproductor de YouTube embebido mediante WebView (https://www.youtube.com/embed/{id})
 * - Fallback / acceso directo "Abrir en YouTube" mediante Intent nativo
 * - Guarda / quita favoritos conectado a ContenidosRepository
 * - Botón "Consultar" con Toast temporal (conectará con WhatsApp en etapa posterior)
 * - Al abrir el video se marca automáticamente como visto
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    videoId: String = "v01",
    viewModel: DetalleViewModel = remember { DetalleViewModel() },
    modifier: Modifier = Modifier,
    onVolverClick: () -> Unit = {},
    onConsultarClick: ((ContenidoMasterMartini?) -> Unit)? = null,
    onVerCursoClick: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Cargar video al entrar y marcar como visto
    LaunchedEffect(videoId) {
        viewModel.cargarVideo(videoId)
    }

    val video by viewModel.video.collectAsState()
    val esGuardado by viewModel.esGuardado.collectAsState()
    var errorEmbebido by remember { mutableStateOf(false) }

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
            // Barra inferior fija con botones de acción (mínimo 48dp de altura)
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
                    // Botón Guardar / Quitar conectado al repositorio
                    OutlinedButton(
                        onClick = { viewModel.toggleGuardado() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (esGuardado) VerdeFondo.copy(alpha = 0.08f) else Blanco
                        )
                    ) {
                        Icon(
                            imageVector = if (esGuardado) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (esGuardado) "Quitar de guardados" else "Guardar video",
                            tint = if (esGuardado) VerdeLetras else NegroLetra,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (esGuardado) "Guardado" else "Guardar",
                            color = if (esGuardado) VerdeLetras else NegroLetra,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Botón Consultar (Toast temporal mientras se integra WhatsApp)
                    Button(
                        onClick = {
                            if (onConsultarClick != null) {
                                onConsultarClick(video)
                            } else {
                                val prod = video?.producto ?: "producto"
                                Toast.makeText(
                                    context,
                                    "Consultando asesoría técnica por $prod...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
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
            val v = video
            if (v != null) {
                // 1. Reproductor de YouTube con WebView embebido (formato 16:9)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    if (!errorEmbebido) {
                        AndroidView(
                            factory = { ctx ->
                                WebView(ctx).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    settings.javaScriptEnabled = true
                                    settings.domStorageEnabled = true
                                    settings.loadWithOverviewMode = true
                                    settings.useWideViewPort = true
                                    webChromeClient = WebChromeClient()
                                    webViewClient = object : WebViewClient() {
                                        override fun onReceivedError(
                                            view: WebView?,
                                            errorCode: Int,
                                            description: String?,
                                            failingUrl: String?
                                        ) {
                                            errorEmbebido = true
                                        }
                                    }
                                    loadUrl("https://www.youtube.com/embed/${v.youtubeVideoId}?autoplay=0&rel=0")
                                }
                            },
                            update = { webView ->
                                webView.loadUrl("https://www.youtube.com/embed/${v.youtubeVideoId}?autoplay=0&rel=0")
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Fallback si no se puede reproducir embebido
                    if (errorEmbebido) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "No se pudo cargar el reproductor embebido",
                                color = Blanco,
                                fontSize = 13.sp
                            )
                            Button(
                                onClick = {
                                    val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${v.youtubeVideoId}"))
                                    val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${v.youtubeVideoId}"))
                                    try {
                                        context.startActivity(intentApp)
                                    } catch (_: Exception) {
                                        context.startActivity(intentWeb)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Dorado)
                            ) {
                                Text("Abrir en YouTube", color = Blanco, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Botón complementario para abrir en la app de YouTube directamente
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Blanco
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Reproducir externamente:",
                            fontSize = 12.sp,
                            color = NegroLetra.copy(alpha = 0.65f)
                        )
                        TextButton(
                            onClick = {
                                val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${v.youtubeVideoId}"))
                                val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${v.youtubeVideoId}"))
                                try {
                                    context.startActivity(intentApp)
                                } catch (_: Exception) {
                                    context.startActivity(intentWeb)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInNew,
                                contentDescription = "Abrir en YouTube",
                                modifier = Modifier.size(16.dp),
                                tint = VerdeLetras
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Abrir en YouTube",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VerdeLetras
                            )
                        }
                    }
                }

                // 2. Ficha técnica del video
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
                            text = v.categoria,
                            color = VerdeLetras,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "  ·  ${v.duracion}",
                            color = NegroLetra.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                    }

                    // Título
                    Text(
                        text = v.titulo,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NegroLetra,
                        lineHeight = 26.sp
                    )

                    // Descripción ("Video con {producto}")
                    Text(
                        text = v.descripcion,
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
                                text = v.producto,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = VerdeLetras
                            )

                            Text(
                                text = "Línea oficial Master Martini aplicada en esta preparación técnica.",
                                fontSize = 13.sp,
                                color = NegroLetra.copy(alpha = 0.75f),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    // 4. Tarjeta de Curso asociado en Escuela Única
                    val cursoAsociado = viewModel.obtenerCursoAsociado()
                    if (cursoAsociado != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onVerCursoClick?.invoke(cursoAsociado.id) },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = GrisFondoSuave)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = "MÓDULO DE ESCUELA ÚNICA",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Dorado
                                    )
                                    Text(
                                        text = cursoAsociado.titulo,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VerdeLetras
                                    )
                                    Text(
                                        text = cursoAsociado.descripcion,
                                        fontSize = 12.sp,
                                        color = NegroLetra.copy(alpha = 0.7f),
                                        maxLines = 1
                                    )
                                }
                                Text(
                                    text = "Ver curso →",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Dorado
                                )
                            }
                        }
                    }
                }
            } else {
                // Estado mientras carga el video
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = VerdeFondo)
                }
            }
        }
    }
}

@Preview(name = "Vista 5 - Detalle Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun DetalleScreenPreview() {
    App_GrupoSoPaTheme {
        DetalleScreen(videoId = "v01")
    }
}
