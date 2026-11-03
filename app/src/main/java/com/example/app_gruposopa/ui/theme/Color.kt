package com.example.app_gruposopa.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

// Lo cambia el ajuste Alto contraste, las pantallas se redibujan solas al cambiar
var altoContrasteActivo by mutableStateOf(false)

// Colores
val VerdeFondo: Color                        // #004D42 (Verde oficial)
    get() = if (altoContrasteActivo) Color(0xFF00261F) else Color(0xFF004D42)
val Blanco = Color(0xFFFFFFFF)               // #FFFFFF (Blanco)
val BlancoFondo = Color(0xFFFBF9F5)          // #FBF9F5 Blanco alternativo
val Crema = Color(0xFFF4EFE6)                // #F4EFE6 Blanco Crema
val Dorado: Color                            // #B69F58 (Dorado)
    get() = if (altoContrasteActivo) Color(0xFFE8CC73) else Color(0xFFB69F58)
val DoradoTexto: Color                       // #7A6118 Dorado oscuro para textos sobre fondo claro
    get() = if (altoContrasteActivo) Color(0xFF4A3A00) else Color(0xFF7A6118)
val VerdeLetras: Color                       // #004D42 (Verde para textos)
    get() = if (altoContrasteActivo) Color(0xFF00261F) else Color(0xFF004D42)
val LetrasEnFondoVerde: Color                // #FFFFFF99 (Letras sobre fondo verde)
    get() = if (altoContrasteActivo) Color(0xFFFFFFFF) else Color(0x99FFFFFF)
val NegroLetra: Color                        // #141617 (Negro letra principal)
    get() = if (altoContrasteActivo) Color(0xFF000000) else Color(0xFF141617)

// Accesorios
val GrisFondoSuave = Color(0xFFF6F8F7)       // Fondo sutil para tarjetas y contrastes

// Texto más claro, salvo con Alto contraste
fun Color.suave(alpha: Float): Color = if (altoContrasteActivo) this else copy(alpha = alpha)

// Dorado para dibujos sobre fondo claro, con Alto contraste se oscurece
val DoradoSobreClaro: Color
    get() = if (altoContrasteActivo) DoradoTexto else Dorado
