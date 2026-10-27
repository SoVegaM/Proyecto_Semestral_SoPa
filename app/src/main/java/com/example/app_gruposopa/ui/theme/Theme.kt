package com.example.app_gruposopa.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
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
    onSurfaceVariant = NegroLetra.copy(alpha = 0.7f)
)

// Siempre tema claro, con los colores de la marca
@Composable
fun App_GrupoSoPaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
