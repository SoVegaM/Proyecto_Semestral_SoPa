package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_gruposopa.R
import com.example.app_gruposopa.ui.theme.*

/**
 * Vista 1 · Bienvenida (Entrada con marca)
 * Diseño para celular compacto según el wireframe oficial del MVP:
 * - Fondo verde corporativo (#004D42)
 * - Logo central con marco dorado
 * - "Centro técnico gastronómico"
 * - Lema "Qualità italiana"
 * - Botón "Comenzar" accesible (>= 48dp)
 */
@Composable
fun BienvenidaScreen(
    onComenzarClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp)
    ) {
        // Bloque central (Logo, Título y Lema)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Contenedor del Logo con borde sutil dorado estilo tarjeta institucional
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.5.dp, Dorado, RoundedCornerShape(12.dp)),
                color = VerdeFondo,
                shadowElevation = 4.dp
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo Master Martini",
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth(),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Título institucional
            Text(
                text = "Centro técnico gastronómico",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Blanco,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            // Lema en dorado / cursiva suave
            Text(
                text = "Qualità italiana",
                fontSize = 17.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                color = Dorado,
                textAlign = TextAlign.Center
            )
        }

        // Botón inferior "Comenzar"
        Button(
            onClick = onComenzarClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Dorado,
                contentColor = NegroLetra
            ),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = "Comenzar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Blanco
            )
        }
    }
}

@Preview(name = "Vista 1 - Bienvenida Compacta", widthDp = 360, heightDp = 800, showBackground = true)
@Composable
fun BienvenidaScreenPreview() {
    App_GrupoSoPaTheme {
        BienvenidaScreen()
    }
}
