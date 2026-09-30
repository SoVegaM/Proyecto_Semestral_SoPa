package com.example.app_gruposopa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_gruposopa.ui.screens.HomeScreen2
import com.example.app_gruposopa.ui.screens.HomeScreenCompacta
import com.example.app_gruposopa.ui.theme.App_GrupoSoPaTheme

/**
 * Actividad principal de la aplicación Master Martini Chile.
 * Configurada para renderizar la interfaz adaptable mediante Window Size Classes
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_GrupoSoPaTheme {
                HomeScreen2()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    App_GrupoSoPaTheme {
        HomeScreenCompacta()
    }
}