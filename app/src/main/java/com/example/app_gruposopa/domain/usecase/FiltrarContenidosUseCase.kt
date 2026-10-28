package com.example.app_gruposopa.domain.usecase

import com.example.app_gruposopa.domain.model.ContenidoMasterMartini

// Filtro de Inicio por categoría y por texto (título o insumo)
class FiltrarContenidosUseCase {

    operator fun invoke(
        contenidos: List<ContenidoMasterMartini>,
        categoria: String,
        busqueda: String
    ): List<ContenidoMasterMartini> {
        val texto = busqueda.trim().lowercase()
        return contenidos.filter { video ->
            val coincideCategoria = categoria == TODAS || video.categoria == categoria
            val coincideTexto = texto.isEmpty() ||
                video.titulo.lowercase().contains(texto) ||
                video.producto.lowercase().contains(texto)
            coincideCategoria && coincideTexto
        }
    }

    companion object {
        const val TODAS = "Todas"
    }
}
