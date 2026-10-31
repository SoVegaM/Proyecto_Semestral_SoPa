package com.example.app_gruposopa.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.ui.theme.*
import com.example.app_gruposopa.ui.components.EtiquetaTipo
import com.example.app_gruposopa.ui.utils.esPantallaAncha
import com.example.app_gruposopa.ui.viewmodels.DetalleViewModel

/**
 * Vista 5: Detalle de contenido. Video, insumo destacado y botones Guardar y Consultar.
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    videoId: String = "v01",
    viewModel: DetalleViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onVolverClick: () -> Unit = {},
    onConsultarClick: ((ContenidoMasterMartini?) -> Unit)? = null,
    onVerCursoClick: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Carga el video al entrar
    LaunchedEffect(videoId) {
        viewModel.cargarVideo(videoId)
    }

    val video by viewModel.video.collectAsState()
    val esGuardado by viewModel.esGuardado.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    // Último texto, para que no se borre mientras se va la animación
    var ultimoMensaje by remember { mutableStateOf("") }
    mensaje?.let { ultimoMensaje = it }
    // rememberSaveable para que no se pierda al girar el celular
    var errorEmbebido by rememberSaveable { mutableStateOf(false) }

    // Colores animados del botón Guardar
    val fondoGuardar by animateColorAsState(
        targetValue = if (esGuardado) VerdeFondo.copy(alpha = 0.12f) else Blanco,
        animationSpec = tween(400),
        label = "fondoGuardar"
    )
    val colorGuardar by animateColorAsState(
        targetValue = if (esGuardado) VerdeLetras else NegroLetra,
        animationSpec = tween(400),
        label = "colorGuardar"
    )
    var reproductorListo by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = GrisFondoSuave,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detalle",
                        style = MaterialTheme.typography.titleMedium,
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
            // Barra inferior fija con botones de acción
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
                    // Guardar / quitar
                    OutlinedButton(
                        onClick = { viewModel.toggleGuardado() },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = fondoGuardar
                        )
                    ) {
                        Icon(
                            imageVector = if (esGuardado) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (esGuardado) "Quitar de guardados" else "Guardar video",
                            tint = colorGuardar,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (esGuardado) "Guardado" else "Guardar",
                            color = colorGuardar,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    //TODO
                    // Botón consultar temporal
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
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(GrisFondoSuave)
        ) {
            val v = video
            if (v != null) {
                // Reproductor y botón para abrir en YouTube
                val bloqueReproductor: @Composable () -> Unit = {
                    // Reproductor de YouTube
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
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
                                        setBackgroundColor(android.graphics.Color.BLACK)
                                        webChromeClient = WebChromeClient()
                                        webViewClient = object : WebViewClient() {
                                            override fun onPageFinished(view: WebView?, url: String?) {
                                                reproductorListo = true
                                            }

                                            override fun onReceivedError(
                                                view: WebView?,
                                                errorCode: Int,
                                                description: String?,
                                                failingUrl: String?
                                            ) {
                                                errorEmbebido = true
                                            }
                                        }
                                        addJavascriptInterface(
                                            PuenteReproductor { viewModel.marcarComoVisto() },
                                            PuenteReproductor.NOMBRE
                                        )
                                        cargarVideoYoutube(v.youtubeVideoId)
                                    }
                                },
                                update = { webView -> webView.cargarVideoYoutube(v.youtubeVideoId) },
                                modifier = Modifier
                                    .fillMaxSize()
                                    // Transparente hasta que cargue x bug visual (pantalla negra).
                                    // Va en su propia capa para que no afecte al resto de la pantalla
                                    .graphicsLayer { alpha = if (reproductorListo) 1f else 0f }
                            )
                            if (!reproductorListo) {
                                CircularProgressIndicator(color = Dorado)
                            }
                        }

                        // Si el reproductor falla, se ofrece abrirlo en YouTube
                        if (errorEmbebido) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "No se pudo cargar el reproductor embebido",
                                    color = Blanco,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Button(
                                    onClick = {
                                        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${v.youtubeVideoId}"))
                                        val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${v.youtubeVideoId}"))
                                        viewModel.marcarComoVisto()
                                        try {
                                            context.startActivity(intentApp)
                                        } catch (_: Exception) {
                                            context.startActivity(intentWeb)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Dorado)
                                ) {
                                    Text("Abrir en YouTube", color = NegroLetra, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // Abrir en la app de YouTube
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
                                style = MaterialTheme.typography.bodySmall,
                                color = NegroLetra.copy(alpha = 0.65f)
                            )
                            TextButton(
                                onClick = {
                                    val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:${v.youtubeVideoId}"))
                                    val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${v.youtubeVideoId}"))
                                    viewModel.marcarComoVisto()
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
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VerdeLetras
                                )
                            }
                        }
                    }
                }

                // Datos del video, insumo y curso
                val bloqueInfo: @Composable () -> Unit = {
                    // Datos del video
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
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "  -  ${v.duracion}",
                                color = NegroLetra.copy(alpha = 0.6f),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        EtiquetaTipo(v.tipo)

                        Text(
                            text = v.titulo,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = NegroLetra
                        )

                        Text(
                            text = v.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = NegroLetra.copy(alpha = 0.85f)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Insumo destacado
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
                                        color = DoradoTexto,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Text(
                                    text = v.producto,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = VerdeLetras
                                )

                                Text(
                                    text = "Insumo Master Martini usado en esta preparación.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = NegroLetra.copy(alpha = 0.75f)
                                )
                            }
                        }

                        // Curso al que pertenece el video
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
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = DoradoTexto
                                        )
                                        Text(
                                            text = cursoAsociado.titulo,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = VerdeLetras
                                        )
                                        Text(
                                            text = cursoAsociado.descripcion,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = NegroLetra.copy(alpha = 0.7f),
                                            maxLines = 1
                                        )
                                    }
                                    Text(
                                        text = "Ver curso →",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = DoradoTexto
                                    )
                                }
                            }
                        }
                    }
                }

                // En tablet o celular girado: video a la izquierda e información a la derecha
                if (esPantallaAncha()) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .weight(1.1f)
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState())
                        ) { bloqueReproductor() }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .verticalScroll(scrollState)
                        ) { bloqueInfo() }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        bloqueReproductor()
                        bloqueInfo()
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

            // Aviso animado al guardar o quitar
            AnimatedVisibility(
                visible = mensaje != null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                Surface(
                    color = NegroLetra.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Dorado
                        )
                        Text(
                            text = ultimoMensaje,
                            color = Blanco,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

/**
 * El JavaScript del reproductor llama a alReproducir() cuando parte el video.
 */
private class PuenteReproductor(private val onReproducir: () -> Unit) {
    @JavascriptInterface
    fun alReproducir() = onReproducir()

    companion object {
        const val NOMBRE = "PuenteReproductor"
    }
}

/**
 * Carga el video de YouTube en el WebView.
 * Va con loadDataWithBaseURL y un origen https porque si no YouTube da "Error 153".
 */
private fun WebView.cargarVideoYoutube(youtubeVideoId: String) {
    if (tag == youtubeVideoId) return
    tag = youtubeVideoId
    val origen = "https://" + context.packageName.replace('_', '-')
    val html = """
        <!DOCTYPE html>
        <html>
        <head>
          <meta name="viewport" content="width=device-width, initial-scale=1">
          <meta name="referrer" content="strict-origin-when-cross-origin">
          <style>
            html, body { margin: 0; padding: 0; height: 100%; background: #000; overflow: hidden; }
            #reproductor { position: absolute; inset: 0; width: 100%; height: 100%; }
          </style>
        </head>
        <body>
          <div id="reproductor"></div>
          <script>
            var avisado = false;
            function onYouTubeIframeAPIReady() {
              new YT.Player('reproductor', {
                width: '100%',
                height: '100%',
                videoId: '$youtubeVideoId',
                playerVars: { rel: 0, playsinline: 1, origin: '$origen' },
                events: {
                  onStateChange: function (e) {
                    if (e.data === YT.PlayerState.PLAYING && !avisado) {
                      avisado = true;
                      ${PuenteReproductor.NOMBRE}.alReproducir();
                    }
                  }
                }
              });
            }
          </script>
          <script src="https://www.youtube.com/iframe_api"></script>
        </body>
        </html>
    """.trimIndent()
    loadDataWithBaseURL(origen, html, "text/html", "utf-8", null)
}

@Preview(name = "Vista 5 - Detalle Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun DetalleScreenPreview() {
    App_GrupoSoPaTheme {
        DetalleScreen(videoId = "v01")
    }
}
