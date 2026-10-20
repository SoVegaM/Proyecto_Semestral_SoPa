package com.example.app_gruposopa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Escala tipográfica accesible de Master Martini.
 * Pensada para usuarios mayores de 50: ningún texto bajo 13 sp y cuerpo base de 16 sp.
 * Las pantallas deben usar estos estilos (MaterialTheme.typography.*) en vez de fontSize fijo,
 * para que la Vista 9 · Accesibilidad pueda escalarlos desde el tema.
 */
private fun estilo(tamano: Int, interlineado: Int, peso: FontWeight = FontWeight.Normal) = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = peso,
    fontSize = tamano.sp,
    lineHeight = interlineado.sp,
    letterSpacing = 0.15.sp
)

val Typography = Typography(
    headlineSmall = estilo(26, 32, FontWeight.Bold),
    titleLarge = estilo(23, 30, FontWeight.Bold),
    titleMedium = estilo(19, 26, FontWeight.SemiBold),
    titleSmall = estilo(17, 24, FontWeight.SemiBold),
    bodyLarge = estilo(18, 26),
    bodyMedium = estilo(16, 24),
    bodySmall = estilo(14, 20),
    labelLarge = estilo(16, 22, FontWeight.Medium),
    labelMedium = estilo(14, 20, FontWeight.Medium),
    labelSmall = estilo(13, 18, FontWeight.Medium)
)
