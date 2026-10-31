package com.example.app_gruposopa.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_gruposopa.R
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.model.TipoContenido
import com.example.app_gruposopa.ui.theme.*

// Partes de Inicio que comparten los tres diseños

@Composable
fun BarraSuperiorInicio(onGuardadosClick: () -> Unit, onMenuClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Blanco,
        shadowElevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Master Martini",
                modifier = Modifier
                    .height(34.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )

            // Abrir el menú lateral
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menú",
                    tint = VerdeLetras
                )
            }

            // Ir a Mis guardados
            IconButton(
                onClick = onGuardadosClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Mis guardados",
                    tint = DoradoTexto
                )
            }
        }
    }
}

@Composable
fun EncabezadoInicio() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Centro Técnico Gastronómico",
            color = VerdeLetras,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Recetas, videos y asesoría técnica para panaderías, pastelerías, heladerías y restaurantes.",
            style = MaterialTheme.typography.bodyMedium,
            color = NegroLetra
        )
    }
}

// Banner que lleva a Cursos
@Composable
fun BannerEscuelaUnica(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = VerdeFondo),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Dorado,
                    modifier = Modifier.size(26.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "ESCUELA ÚNICA",
                        color = Dorado,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "6 cursos de formación profesional",
                        color = Blanco,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Dorado
            ) {
                Text(
                    text = "Ver Cursos →",
                    color = NegroLetra,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

// Buscador (por título o producto)
@Composable
fun BuscadorContenidos(texto: String, onTextoChange: (String) -> Unit) {
    OutlinedTextField(
        value = texto,
        onValueChange = onTextoChange,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp),
        placeholder = {
            Text(
                text = "Buscar receta o producto",
                style = MaterialTheme.typography.bodyMedium,
                color = NegroLetra.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = VerdeLetras
            )
        },
        trailingIcon = {
            if (texto.isNotEmpty()) {
                IconButton(onClick = { onTextoChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Limpiar búsqueda",
                        tint = NegroLetra.copy(alpha = 0.6f)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Blanco,
            unfocusedContainerColor = Blanco,
            focusedBorderColor = VerdeFondo,
            unfocusedBorderColor = GrisFondoSuave
        )
    )
}

@Composable
fun TituloSeccion(texto: String) {
    Text(
        text = texto,
        color = VerdeLetras,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ChipCategoria(
    categoria: String,
    seleccionada: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = seleccionada,
        onClick = onClick,
        modifier = modifier,
        label = {
            Text(
                text = categoria,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Normal,
                color = if (seleccionada) Blanco else NegroLetra
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = VerdeFondo,
            containerColor = Blanco
        )
    )
}

// "Contenidos Disponibles" con la cantidad de resultados
@Composable
fun ContadorResultados(cantidad: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TituloSeccion("Contenidos Disponibles")
        Text(
            text = if (cantidad == 1) "1 encontrado" else "$cantidad encontrados",
            color = NegroLetra.copy(alpha = 0.6f),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SinResultados() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No se encontraron videos para tu búsqueda.",
                color = NegroLetra.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

// Etiqueta "Receta" o "Técnica"
@Composable
fun EtiquetaTipo(tipo: TipoContenido) {
    Surface(
        color = VerdeFondo.copy(alpha = 0.08f),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = tipo.etiqueta,
            color = VerdeLetras,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun TarjetaContenido(
    item: ContenidoMasterMartini,
    onClick: () -> Unit,
    alturaMiniatura: Dp = 140.dp
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Miniatura del video (Coil)
            AsyncImage(
                model = item.miniaturaUrl,
                contentDescription = "Miniatura de ${item.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(alturaMiniatura)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            // Fila de categoría y duración
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Dorado.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = item.categoria,
                        color = DoradoTexto,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        maxLines = 1
                    )
                }
                Text(
                    text = "⏱ ${item.duracion}",
                    color = NegroLetra.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            EtiquetaTipo(item.tipo)

            Text(
                text = item.titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = NegroLetra,
                maxLines = 2
            )

            Text(
                text = item.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = NegroLetra.copy(alpha = 0.8f),
                maxLines = 2
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp),
                color = GrisFondoSuave
            )

            // Insumo destacado y acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Insumo: ${item.producto}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = VerdeLetras,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Ver video ▶",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = DoradoTexto
                )
            }
        }
    }
}
