package com.example.app_gruposopa.domain.usecase

import com.example.app_gruposopa.data.repository.ContenidosRepository
import com.example.app_gruposopa.domain.model.ContenidoMasterMartini

/**
 * Devuelve todos los contenidos del repositorio.
 */
class GetContenidosUseCase(
    private val repository: ContenidosRepository = ContenidosRepository.instance
) {
    operator fun invoke(): List<ContenidoMasterMartini> {
        return repository.obtenerVideos()
    }
}
