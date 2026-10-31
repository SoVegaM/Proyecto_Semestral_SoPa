package com.example.app_gruposopa.ui.utils

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode

// Dice si la pantalla es Compact, Medium o Expanded
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    return calculateWindowSizeClass(LocalActivity.current as Activity)
}

// true si la pantalla es Medium o Expanded (tablet o celular girado).
// En los Previews no hay Activity, así que ahí se mira el ancho del preview
@Composable
fun esPantallaAncha(): Boolean {
    if (LocalInspectionMode.current) return LocalConfiguration.current.screenWidthDp >= 600
    return obtenerWindowSizeClass().widthSizeClass != WindowWidthSizeClass.Compact
}
