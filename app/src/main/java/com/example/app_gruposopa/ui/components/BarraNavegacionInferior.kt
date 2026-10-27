package com.example.app_gruposopa.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.theme.Blanco
import com.example.app_gruposopa.ui.theme.NegroLetra
import com.example.app_gruposopa.ui.theme.VerdeFondo
import com.example.app_gruposopa.ui.theme.VerdeLetras

// Barra de abajo que usan las pantallas principales
@Composable
fun BarraNavegacionInferior(
    rutaActual: String,
    onNavegar: (String) -> Unit
) {
    NavigationBar(
        containerColor = Blanco,
        tonalElevation = 0.dp
    ) {
        AppScreen.vistasBarraInferior.forEach { pantalla ->
            val seleccionada = pantalla.route == rutaActual
            NavigationBarItem(
                selected = seleccionada,
                onClick = { if (!seleccionada) onNavegar(pantalla.route) },
                icon = {
                    // sin descripción, si no TalkBack lee el nombre dos veces
                    pantalla.icono?.let { Icon(it, contentDescription = null) }
                },
                label = {
                    Text(
                        text = pantalla.titulo,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (seleccionada) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeLetras,
                    selectedTextColor = VerdeLetras,
                    indicatorColor = VerdeFondo.copy(alpha = 0.12f),
                    unselectedIconColor = NegroLetra.copy(alpha = 0.7f),
                    unselectedTextColor = NegroLetra.copy(alpha = 0.7f)
                )
            )
        }
    }
}
