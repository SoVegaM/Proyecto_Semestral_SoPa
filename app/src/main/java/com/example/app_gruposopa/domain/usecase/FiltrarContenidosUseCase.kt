package com.example.app_gruposopa.domain.usecase

import java.text.Normalizer
import com.example.app_gruposopa.domain.model.Categorias
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini

// Filtro de Inicio por categoría y por texto (título o insumo)
class FiltrarContenidosUseCase {

    operator fun invoke(
        contenidos: List<ContenidoMasterMartini>,
        categoria: String,
        busqueda: String
    ): List<ContenidoMasterMartini> {
        val texto = normalizar(busqueda.trim())
        return contenidos.filter { video ->
            val coincideCategoria = categoria == Categorias.todas || video.categoria == categoria
            val coincideTexto = texto.isEmpty() ||
                normalizar(video.titulo).contains(texto) ||
                normalizar(video.producto).contains(texto)
            coincideCategoria && coincideTexto
        }
    }

    // Sin tildes y en minúscula, así "gratin" encuentra "Gratín"
    private fun normalizar(texto: String): String =
        Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace(Regex("\\p{Mn}+"), "")
            .lowercase()
}
