package com.example.app_gruposopa.data.repository

import android.content.Context
import com.example.app_gruposopa.data.local.ContenidosLocalDataSource
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini
import com.example.app_gruposopa.domain.model.Curso
import com.example.app_gruposopa.domain.model.TipoContenido
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Repositorio con los videos y cursos.
 * Los guardados y los vistos se guardan en el celular con DataStore.
 */
class ContenidosRepository {

    companion object {
        // Categorías
        const val CAT_CREMAS = "Cremas Vegetales UHT"
        const val CAT_CHOCO = "Chocolates & Sucedáneos"
        const val CAT_GELATO = "Gelatería"
        const val CAT_PAN = "Panadería & Pastelería"

        // Una sola instancia, así todas las pantallas ven los mismos datos
        val instance: ContenidosRepository by lazy { ContenidosRepository() }

        // Videos de técnica o usos de un insumo (el resto son recetas)
        // Tiene que ir antes de LISTA_VIDEOS porque se usa al crearla
        private val IDS_TECNICAS = setOf("v03", "v04", "v05", "v11", "v12", "v13", "v22", "v25")

        // Videos del canal de YouTube de Master Martini
        private val LISTA_VIDEOS: List<ContenidoMasterMartini> = listOf(
            crearVideo("v01", "Leche Asada", "Bravo Crem Azucarada", "1:16", CAT_CREMAS, "-tH8LKMzGN0"),
            crearVideo("v02", "Mousse frutilla", "Bravo Crem Azucarada", "0:38", CAT_CREMAS, "q9gFPtzzZe4"),
            crearVideo("v03", "¿Cómo utilizar la crema Vegetal?", "Bravo Crem Azucarada", "0:43", CAT_CREMAS, "3n-h0n7Ttks"),
            crearVideo("v04", "¿Cómo utilizar crema pastelera, merengue o crema chantilly EN POLVO?", "Delice", "1:01", CAT_PAN, "1U1twaw6bP0"),
            crearVideo("v05", "Calda Delicia: Usos", "Calda Delicia", "0:39", CAT_PAN, "KCYCWkBpxR4"),
            crearVideo("v06", "Frutti di mare", "Bravo Crem Cocina", "1:00", CAT_CREMAS, "Mxy3SInyk8Y"),
            crearVideo("v07", "Gratín de Papas", "Bravo Crem Cocina", "0:52", CAT_CREMAS, "UJAF5sEBgnA"),
            crearVideo("v08", "Crema de Brocoli", "Bravo Crem Cocina", "1:01", CAT_CREMAS, "y1mJISdxwm8"),
            crearVideo("v09", "Lomo strogonoff", "Bravo Crem Cocina", "1:01", CAT_CREMAS, "Qwv-Uxbivqw"),
            crearVideo("v10", "Royale para quiches", "Bravo Crem Cocina", "1:08", CAT_CREMAS, "7i2UkZ0AHMg"),
            crearVideo("v11", "Tips y Usos del Chocolate con Chef Lili Cañete", "Caravella", "8:18", CAT_CHOCO, "v_WinlHRQt0"),
            crearVideo("v12", "¡Qué fácil es con Chocolate Caravella!", "Caravella", "0:25", CAT_CHOCO, "C1HaDB9US_Y"),
            crearVideo("v13", "Caravella Nocciola", "Caravella Nocciola", "0:28", CAT_CHOCO, "lvL9R-g5LGw"),
            crearVideo("v14", "Torta Chocolate Avellana", "Caravella Deco", "1:17", CAT_CHOCO, "ZwOkabO177M"),
            crearVideo("v15", "Mousse chocolate blanco", "Chocolate Ariba", "1:02", CAT_CHOCO, "-8KFtkMyntY"),
            crearVideo("v16", "Helado Artesanal Frutos del Bosque", "Martini Gelato", "0:56", CAT_GELATO, "R2N_oOG0xI8"),
            crearVideo("v17", "Helado Artesanal de Avellana", "Base Blanca Martini Gelato", "1:01", CAT_GELATO, "KhhA9pAJZxA"),
            crearVideo("v18", "Café helado", "Base Vainilla Martini Gelato", "0:50", CAT_GELATO, "mGTlgfD_x5Y"),
            crearVideo("v19", "Preparación sorbete detox", "Martini Gelato", "0:45", CAT_GELATO, "4Zt0f_24fXY"),
            crearVideo("v20", "Masa hojaldre", "Master Export Hoja", "1:51", CAT_PAN, "1yv3Kb7KdEk"),
            crearVideo("v21", "Queque navideño", "Vita Bake", "1:21", CAT_PAN, "xoTHn4_-Fhs"),
            crearVideo("v22", "Master Shine: Usos", "Master Shine", "0:44", CAT_PAN, "OO4WLgcfB3E"),
            crearVideo("v23", "Fetuccini Setas", "Orasì Bio Organic", "0:55", CAT_CREMAS, "9jJvCFPKSss"),
            crearVideo("v24", "Arroz con Leche", "Orasì Bio Organic", "0:38", CAT_CREMAS, "K45qNIq85oA"),
            crearVideo("v25", "Orasì Barista: Usos", "Orasì Barista", "0:42", CAT_CREMAS, "gjonIyJV9T0"),
            crearVideo("v26", "Pudin de Chía", "Orasì Coco", "0:55", CAT_CREMAS, "8n7kAGbS5uE"),
            crearVideo("v27", "Panna Cotta", "Orasì Barista", "1:00", CAT_CREMAS, "CdSAJaKY1cY"),
            crearVideo("v28", "Crema de zapallo", "Orasì", "0:56", CAT_CREMAS, "2J_Pj--qzjo"),
            crearVideo("v29", "Tarta de coco", "Orasì Coco", "1:07", CAT_CREMAS, "vmC2JpTvrLM"),
            crearVideo("v30", "Pancake de Avena y Plátano con Chef Lili Cañete", "Orasì", "4:48", CAT_CREMAS, "Dz20QKTeK5I")
        )

        // Cada curso agrupa videos por su id
        private val LISTA_CURSOS: List<Curso> = listOf(
            Curso(
                id = "c1",
                titulo = "Pastelería y cremas",
                categoria = CAT_CREMAS,
                descripcion = "Postres y preparaciones con cremas vegetales Bravo Crem y productos Delice.",
                videoIds = listOf("v01", "v02", "v03", "v04", "v05")
            ),
            Curso(
                id = "c2",
                titulo = "Cocina salada con Bravo Crem",
                categoria = CAT_CREMAS,
                descripcion = "Recetas saladas con Bravo Crem Cocina: gratines, cremas y salsas.",
                videoIds = listOf("v06", "v07", "v08", "v09", "v10")
            ),
            Curso(
                id = "c3",
                titulo = "Chocolatería",
                categoria = CAT_CHOCO,
                descripcion = "Técnicas y recetas con las coberturas y chocolates Caravella y Ariba.",
                videoIds = listOf("v11", "v12", "v13", "v14", "v15")
            ),
            Curso(
                id = "c4",
                titulo = "Heladería artesanal",
                categoria = CAT_GELATO,
                descripcion = "Helados y sorbetes artesanales con las bases Martini Gelato.",
                videoIds = listOf("v16", "v17", "v18", "v19")
            ),
            Curso(
                id = "c5",
                titulo = "Panadería y masas",
                categoria = CAT_PAN,
                descripcion = "Masas de hojaldre y productos de horneo.",
                videoIds = listOf("v20", "v21", "v22")
            ),
            Curso(
                id = "c6",
                titulo = "Recetas plant based con Orasì",
                categoria = CAT_CREMAS,
                descripcion = "Recetas dulces y saladas con las bebidas vegetales Orasì.",
                videoIds = listOf("v23", "v24", "v25", "v26", "v27", "v28", "v29", "v30")
            )
        )

        private fun crearVideo(
            id: String,
            titulo: String,
            producto: String,
            duracion: String,
            categoria: String,
            youtubeVideoId: String
        ): ContenidoMasterMartini {
            return ContenidoMasterMartini(
                id = id,
                titulo = titulo,
                descripcion = "Video con $producto",
                duracion = duracion,
                categoria = categoria,
                producto = producto,
                youtubeVideoId = youtubeVideoId,
                tipo = if (id in IDS_TECNICAS) TipoContenido.TECNICA else TipoContenido.RECETA
            )
        }
    }

    private var almacenamiento: ContenidosLocalDataSource? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Ids de videos guardados
    private val _guardados = MutableStateFlow<Set<String>>(emptySet())
    val guardados: StateFlow<Set<String>> = _guardados.asStateFlow()

    // Ids de videos vistos
    private val _videosVistos = MutableStateFlow<Set<String>>(emptySet())
    val videosVistos: StateFlow<Set<String>> = _videosVistos.asStateFlow()

    // Carga lo que había guardado y guarda cada cambio. Se llama en MainActivity
    // (en los Previews no se llama, ahí todo queda en memoria)
    fun conectarAlmacenamiento(context: Context) {
        if (almacenamiento != null) return
        val local = ContenidosLocalDataSource(context)
        almacenamiento = local
        scope.launch {
            _guardados.update { it + local.leerGuardados() }
            _videosVistos.update { it + local.leerVideosVistos() }
            launch { guardados.collect { local.escribirGuardados(it) } }
            launch { videosVistos.collect { local.escribirVideosVistos(it) } }
        }
    }

    fun obtenerVideos(): List<ContenidoMasterMartini> = LISTA_VIDEOS

    fun obtenerVideoPorId(id: String): ContenidoMasterMartini? {
        return LISTA_VIDEOS.find { it.id == id }
    }

    fun obtenerCursos(): List<Curso> = LISTA_CURSOS

    fun obtenerVideosPorIds(ids: List<String>): List<ContenidoMasterMartini> {
        val map = LISTA_VIDEOS.associateBy { it.id }
        return ids.mapNotNull { map[it] }
    }

    fun toggleGuardado(videoId: String) {
        _guardados.update { actual ->
            if (actual.contains(videoId)) actual - videoId else actual + videoId
        }
    }

    fun esGuardado(videoId: String): Boolean {
        return _guardados.value.contains(videoId)
    }

    fun marcarComoVisto(videoId: String) {
        _videosVistos.update { it + videoId }
    }

    fun obtenerCursoPorVideoId(videoId: String): Curso? {
        return LISTA_CURSOS.find { it.videoIds.contains(videoId) }
    }
}
