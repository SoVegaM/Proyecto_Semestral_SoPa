package com.example.app_gruposopa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.navigation.AppNavHost
import com.example.app_gruposopa.ui.theme.App_GrupoSoPaTheme

/**
 * Actividad principal: aplica el tema y carga la navegación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ContenidosRepository.instance.conectarAlmacenamiento(applicationContext)
        setContent {
            App_GrupoSoPaTheme {
                AppNavHost()
            }
        }
    }
}