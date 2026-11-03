package com.example.app_gruposopa.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

private fun esquemaDeColores() = lightColorScheme(
    primary = VerdeFondo,
    onPrimary = Blanco,
    primaryContainer = VerdeFondo,
    onPrimaryContainer = Blanco,
    secondary = Dorado,
    onSecondary = Blanco,
    secondaryContainer = GrisFondoSuave,
    onSecondaryContainer = NegroLetra,
    background = Blanco,
    surface = Blanco,
    surfaceVariant = GrisFondoSuave,
    onSurface = NegroLetra,
    onSurfaceVariant = NegroLetra.suave(0.7f)
)

// Siempre tema claro, con los colores de la marca y los ajustes de Accesibilidad
@Composable
fun App_GrupoSoPaTheme(
    tamanoTexto: Float = Configuracion.tamanoTextoBase,
    altoContraste: Boolean = false,
    textoNegrita: Boolean = false,
    content: @Composable () -> Unit
) {
    SideEffect { altoContrasteActivo = altoContraste }

    // Escala todos los textos de la app, sin cambiar el tamaño de lo demás
    val densidad = LocalDensity.current
    val densidadConTexto = Density(
        density = densidad.density,
        fontScale = densidad.fontScale * tamanoTexto / Configuracion.tamanoTextoBase
    )

    MaterialTheme(
        colorScheme = esquemaDeColores(),
        typography = if (textoNegrita) TypographyNegrita else Typography
    ) {
        CompositionLocalProvider(LocalDensity provides densidadConTexto, content = content)
    }
}
