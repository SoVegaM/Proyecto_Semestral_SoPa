package com.example.app_gruposopa.repository

import com.example.app_gruposopa.model.ContenidoMasterMartini
import com.example.app_gruposopa.model.TipoContenido

/**
 * Repositorio con los contenidos técnicos, cursos y recetas oficiales de Master Martini Chile / Unigrà.
 * Datos inspirados en las líneas de negocio reales:
 * Panadería & Pastelería, Gelatería, Chocolatería, Cremas UHT y Escuela Unica.
 */
class ContenidoRepository {

    fun obtenerContenidos(): List<ContenidoMasterMartini> {
        return listOf(
            ContenidoMasterMartini(
                id = 1,
                titulo = "Técnicas de Templado de Chocolate Caravella & Ariba",
                categoria = "Chocolates & Sucedáneos",
                descripcion = "Aprende el templado por sembrado para bombones y tabletas con brillo reluciente y quiebre profesional.",
                duracionOPreparacion = "25 min",
                tipo = TipoContenido.CURSO_ESPECIALIDAD,
                insumoDestacado = "Chocolate Puro Ariba & Coberturas Caravella"
            ),
            ContenidoMasterMartini(
                id = 2,
                titulo = "Laminado y Horneado de Croissants y Medialunas",
                categoria = "Panadería & Pastelería",
                descripcion = "Proceso de empastado, vueltas simples y dobles, fermentación y horneado de hojaldre de alta gama.",
                duracionOPreparacion = "45 min",
                tipo = TipoContenido.RECETA,
                insumoDestacado = "Margarinas & Mantecas Especiales Máster"
            ),
            ContenidoMasterMartini(
                id = 3,
                titulo = "Montaje de Vitrina: Mousses y Espejados con Bravo Crem",
                categoria = "Cremas Vegetales UHT",
                descripcion = "Técnicas de batido, estabilidad térmica para vitrina y aplicación de glaseados espejo sobre entremets.",
                duracionOPreparacion = "30 min",
                tipo = TipoContenido.VIDEO_TECNICO,
                insumoDestacado = "Bravo Crem & Decor Up UHT"
            ),
            ContenidoMasterMartini(
                id = 4,
                titulo = "Gelatería Artesanal Italiana: Balance de Bases y Sabores",
                categoria = "Gelatería",
                descripcion = "Formulación de helados cremosos con balance exacto de azúcares, sólidos grasos y pastas aromatizantes.",
                duracionOPreparacion = "40 min",
                tipo = TipoContenido.CURSO_ESPECIALIDAD,
                insumoDestacado = "Bases Neutras & Pastas Concentradas Italianas"
            ),
            ContenidoMasterMartini(
                id = 5,
                titulo = "Masa de Horneo Tradicional para Empanadas",
                categoria = "Panadería & Pastelería",
                descripcion = "Estructura elástica para soportar rellenos húmedos sin romperse durante la cocción.",
                duracionOPreparacion = "35 min",
                tipo = TipoContenido.RECETA,
                insumoDestacado = "Grasa Especial para Masas y Horneo Máster"
            ),
            ContenidoMasterMartini(
                id = 6,
                titulo = "Programa Escuela Unica: Formación Profesional",
                categoria = "Escuela Unica",
                descripcion = "Módulos de especialización técnica con certificación internacional para maestros pasteleros y panaderos.",
                duracionOPreparacion = "60 min",
                tipo = TipoContenido.CURSO_ESPECIALIDAD,
                insumoDestacado = "Metodología Hecho en Italia - Unigrà"
            )
        )
    }
}
