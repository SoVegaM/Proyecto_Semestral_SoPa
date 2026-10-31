package com.example.app_gruposopa.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.R
import com.example.app_gruposopa.ui.theme.*

// Opciones del menú lateral
enum class OpcionMenu { GUARDADOS, SEGUIR_VIENDO, ACCESIBILIDAD, ASESOR, SOBRE, CERRAR_SESION }

@Composable
fun MenuLateral(
    nombreUsuario: String,
    // Título del último video visto, null si todavía no ve ninguno
    ultimoVisto: String?,
    onOpcion: (OpcionMenu) -> Unit
) {
    ModalDrawerSheet(drawerContainerColor = Blanco) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            // Encabezado
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Master Martini",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
            )
            Text(
                text = "Hola, $nombreUsuario",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VerdeLetras,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 8.dp)
            )
            HorizontalDivider(color = GrisFondoSuave)

            TituloGrupo("Mis cosas")
            OpcionDelMenu(Icons.Default.Bookmark, "Mis guardados") { onOpcion(OpcionMenu.GUARDADOS) }
            if (ultimoVisto != null) {
                OpcionDelMenu(Icons.Default.PlayCircle, "Seguir viendo", ultimoVisto) {
                    onOpcion(OpcionMenu.SEGUIR_VIENDO)
                }
            }

            TituloGrupo("Ayuda")
            OpcionDelMenu(Icons.Default.TextFields, "Tamaño de letra y contraste") { onOpcion(OpcionMenu.ACCESIBILIDAD) }
            OpcionDelMenu(Icons.Default.SupportAgent, "Hablar con un asesor") { onOpcion(OpcionMenu.ASESOR) }
            OpcionDelMenu(Icons.Default.Info, "Sobre Master Martini") { onOpcion(OpcionMenu.SOBRE) }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = GrisFondoSuave)
            OpcionDelMenu(Icons.AutoMirrored.Filled.Logout, "Cerrar sesión") { onOpcion(OpcionMenu.CERRAR_SESION) }
        }
    }
}

@Composable
private fun TituloGrupo(texto: String) {
    Text(
        text = texto,
        style = MaterialTheme.typography.labelLarge,
        color = DoradoTexto,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
    )
}

@Composable
private fun OpcionDelMenu(
    icono: ImageVector,
    texto: String,
    detalle: String? = null,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        icon = { Icon(icono, contentDescription = null, tint = VerdeLetras) },
        label = {
            Column {
                Text(text = texto, style = MaterialTheme.typography.bodyLarge, color = NegroLetra)
                if (detalle != null) {
                    Text(
                        text = detalle,
                        style = MaterialTheme.typography.bodySmall,
                        color = NegroLetra.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}
