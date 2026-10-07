package com.example.app_gruposopa.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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


 // Vista 1 - Bienvenida
@Composable
fun BienvenidaScreen(
    onComenzarClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // Mitad Superiorcon Fondo claro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(BlancoFondo)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Master Martini",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Fit
            )
        }

        //Mitad Inferior con Fondo verde porque ADIVINA QUE LOGO TIENE LETRAS BLANCAS.
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.3f),
            color = VerdeFondo
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Bloque central (Título y Logo Italia)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Título
                    Text(
                        text = "Centro técnico gastronómico",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Blanco,
                        textAlign = TextAlign.Center


                    )

                    // Logo Italia
                    Image(
                        painter = painterResource(id = R.drawable.italia),
                        contentDescription = "Logo Italia",
                        modifier = Modifier.height(100.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                // Botón inferior "Comenzar"
                Button(
                    onClick = onComenzarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Dorado,
                        contentColor = Blanco
                    ),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = "Comenzar",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blanco
                    )
                }
            }
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
