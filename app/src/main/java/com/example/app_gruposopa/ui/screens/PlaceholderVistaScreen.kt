package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gruposopa.navigation.AppScreen
import com.example.app_gruposopa.ui.theme.*

/**
 * Pantalla temporal para las vistas que todavía no están hechas (2, 3, 7, 8 y 9).
 */
@Composable
fun PlaceholderVistaScreen(
    pantallaActual: AppScreen,
    onIrAInicio: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GrisFondoSuave)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Blanco),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Dorado.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "VISTA A CARGO DE PERSONA B",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Dorado,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = pantallaActual.nombre,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeLetras,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = pantallaActual.descripcion,
                    fontSize = 13.sp,
                    color = NegroLetra.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onIrAInicio,
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeFondo),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Volver a Inicio (Persona A)", color = Blanco, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
