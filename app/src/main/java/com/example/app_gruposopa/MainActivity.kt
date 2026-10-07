package com.example.app_gruposopa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.app_gruposopa.ui.screens.BienvenidaScreen
import com.example.app_gruposopa.ui.theme.App_GrupoSoPaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_GrupoSoPaTheme {
                BienvenidaScreen()
            }
        }
    }
}