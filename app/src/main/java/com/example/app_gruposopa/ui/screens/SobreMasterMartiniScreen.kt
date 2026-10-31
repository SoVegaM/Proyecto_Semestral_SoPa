package com.example.app_gruposopa.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.R
import com.example.app_gruposopa.ui.theme.*

private const val SITIO_WEB = "https://mastermartini.cl"

/**
 * Sobre Master Martini: quiénes son y enlace a su sitio. Se abre desde el menú lateral.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SobreMasterMartiniScreen(onVolverClick: () -> Unit = {}) {
    val context = LocalContext.current

    Scaffold(
        containerColor = GrisFondoSuave,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sobre Master Martini",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = VerdeFondo)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Master Martini",
                modifier = Modifier.height(90.dp)
            )

            Text(
                text = "Master Martini Chile es parte de Unigrà, empresa italiana con más de 50 años en materias primas para panadería, pastelería, chocolatería y heladería.",
                style = MaterialTheme.typography.bodyLarge,
                color = NegroLetra,
                textAlign = TextAlign.Center
            )

            DatoEmpresa(Icons.Default.Public, "Presente en más de 100 países")
            DatoEmpresa(Icons.Default.Eco, "Productos de origen vegetal, con cultivos propios de soya y arroz")
            DatoEmpresa(Icons.Default.School, "Escuela Única: formación profesional con la excelencia de lo Hecho en Italia")

            Button(
                onClick = {
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(SITIO_WEB)))
                    } catch (_: Exception) {
                        Toast.makeText(context, "No se pudo abrir el navegador", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo, contentColor = Blanco)
            ) {
                Icon(Icons.Default.OpenInNew, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Visitar sitio web",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun DatoEmpresa(icono: ImageVector, texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Blanco, RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Icon(icono, contentDescription = null, tint = DoradoTexto, modifier = Modifier.size(28.dp))
        Text(text = texto, style = MaterialTheme.typography.bodyMedium, color = NegroLetra)
    }
}

@Preview(name = "Sobre Master Martini", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun SobreMasterMartiniPreview() {
    App_GrupoSoPaTheme {
        SobreMasterMartiniScreen()
    }
}
