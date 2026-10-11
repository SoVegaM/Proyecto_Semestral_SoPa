package com.example.app_gruposopa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_gruposopa.navigation.AppNavHost
import com.example.app_gruposopa.ui.theme.App_GrupoSoPaTheme

/**
 * Actividad principal de la aplicación Master Martini Chile.
 * Carga el NavHost central con las 10 rutas del MVP (Fase 0 - Base común).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_GrupoSoPaTheme {
                AppNavHost()
            }
        }
    }
}