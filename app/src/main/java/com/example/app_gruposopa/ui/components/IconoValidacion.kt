package com.example.app_gruposopa.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.app_gruposopa.ui.theme.VerdeLetras

// Ícono al final de un campo: error o correcto. Sin nada escrito no muestra ninguno
@Composable
fun IconoValidacion(error: String?, valor: String) {
    when {
        error != null -> Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = "Campo con error",
            tint = MaterialTheme.colorScheme.error
        )
        valor.isNotBlank() -> Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Campo correcto",
            tint = VerdeLetras
        )
    }
}
