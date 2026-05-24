package com.lasecun.goodwines.features.wine.data.source.mock

import com.lasecun.goodwines.features.wine.domain.model.Wine
import com.lasecun.goodwines.features.wine.domain.model.WineStyle

object MockWineDataSource {
    private val mockWines = listOf(
        Wine(
            id = "wine_1",
            name = "Marqués de Riscal Reserva",
            winery = "Marqués de Riscal",
            vintage = 2019,
            region = "Rioja Alavesa",
            country = "España",
            grapes = listOf("Tempranillo", "Graciano", "Mazuelo"),
            style = WineStyle.RED,
            imageUrl = "https://images.vivino.com/thumbs/NN_g3yYfT_W1st0wC2YVcg_375x500.jpg",
            averageRating = 4.3f,
            abv = 14.0f,
            description = "Vino tinto de gran tradición con cuerpo elegante y complejidad aromática. Notas de frutas maduras, especias y madera equilibradas."
        ),
        Wine(
            id = "wine_2",
            name = "Vega Sicilia Único",
            winery = "Vega Sicilia",
            vintage = 2018,
            region = "Ribera del Duero",
            country = "España",
            grapes = listOf("Tempranillo", "Cabernet Sauvignon", "Merlot"),
            style = WineStyle.RED,
            imageUrl = "https://images.vivino.com/thumbs/4DQZH9HRT0ik6bVD9dZ4Og_375x500.jpg",
            averageRating = 4.7f,
            abv = 14.2f,
            description = "Legendario vino de culto, mezcla compleja de variedades con crianza en madera. Estructura potente y elegancia absoluta."
        ),
        Wine(
            id = "wine_3",
            name = "Priorat DOQ Garnatxa",
            winery = "Scala Dei",
            vintage = 2020,
            region = "Priorat",
            country = "España",
            grapes = listOf("Garnatxa", "Cariñena"),
            style = WineStyle.RED,
            imageUrl = "https://images.vivino.com/thumbs/vEpbN1gTQk66r0B7eVGp6w_375x500.jpg",
            averageRating = 4.5f,
            abv = 15.5f,
            description = "Vino intenso y concentrado del Priorat. Notas de frutas negras, minerales y especias con taninos robustos."
        ),
        Wine(
            id = "wine_4",
            name = "Martín Códax Albariño",
            winery = "Martín Códax",
            vintage = 2023,
            region = "Rías Baixas",
            country = "España",
            grapes = listOf("Albariño"),
            style = WineStyle.WHITE,
            imageUrl = "https://images.vivino.com/thumbs/lZH7FZlQSk27x3eHVGAuDQ_375x500.jpg",
            averageRating = 4.2f,
            abv = 12.5f,
            description = "Blanco fresco y mineral de las Rías Baixas. Aromas cítricos, florales y notas de algas marinas. Acidez perfecta."
        ),
        Wine(
            id = "wine_5",
            name = "Pazo de Señoans Albariño",
            winery = "Pazo de Señoans",
            vintage = 2022,
            region = "Rías Baixas",
            country = "España",
            grapes = listOf("Albariño"),
            style = WineStyle.WHITE,
            imageUrl = "https://images.vivino.com/thumbs/5V4qVm9RTkGQKfBjMc9HJA_375x500.jpg",
            averageRating = 4.4f,
            abv = 13.0f,
            description = "Elegante y expresivo Albariño con identidad clara. Frutas tropicales, toques minerales y una mineralidad cautivadora."
        ),
        Wine(
            id = "wine_6",
            name = "Bodegas Aalto Aalto PS",
            winery = "Bodegas Aalto",
            vintage = 2018,
            region = "Ribera del Duero",
            country = "España",
            grapes = listOf("Tempranillo"),
            style = WineStyle.RED,
            imageUrl = "https://images.vivino.com/thumbs/7Qd1l_zZRkGxN5yJGaFwjw_375x500.jpg",
            averageRating = 4.6f,
            abv = 14.5f,
            description = "Tempranillo puro de máxima expresión. Concentración, poder y elegancia en perfecto equilibrio. Crianza de 18 meses."
        ),
        Wine(
            id = "wine_7",
            name = "Gramona Cava Brut",
            winery = "Gramona",
            vintage = 2020,
            region = "Penedès",
            country = "España",
            grapes = listOf("Macabeo", "Xarel-lo", "Parellada"),
            style = WineStyle.SPARKLING,
            imageUrl = "https://images.vivino.com/thumbs/ySZ8c7ztSE-IXsNz0vgP1g_375x500.jpg",
            averageRating = 4.3f,
            abv = 12.0f,
            description = "Cava tradicional con método clásico. Burbujas finas, aromas complejos de frutas secas, miga de pan y levaduras."
        ),
        Wine(
            id = "wine_8",
            name = "Campo Viejo Reserva",
            winery = "Campo Viejo",
            vintage = 2018,
            region = "Rioja",
            country = "España",
            grapes = listOf("Tempranillo", "Graciano"),
            style = WineStyle.RED,
            imageUrl = "https://images.vivino.com/thumbs/2TE5PJxaQE-oDrQzU4JT8w_375x500.jpg",
            averageRating = 4.1f,
            abv = 13.8f,
            description = "Clásico de la Rioja con personalidad. Crianza en barrica francesa y roble americano. Estructura elegante y expresiva."
        ),
        Wine(
            id = "wine_9",
            name = "Sherry Pedro Ximénez Noe",
            winery = "González Byass",
            vintage = 2015,
            region = "Jerez",
            country = "España",
            grapes = listOf("Pedro Ximénez"),
            style = WineStyle.FORTIFIED,
            imageUrl = "https://images.vivino.com/thumbs/nOsG8pFIS0GHK8eXVLa06g_375x500.jpg",
            averageRating = 4.5f,
            abv = 15.0f,
            description = "Jerez concentrado y dulce con profundidad enorme. Notas de pasas, café, chocolate. Sistema de soleras de 12 años."
        ),
        Wine(
            id = "wine_10",
            name = "Borsao Garnatxa",
            winery = "Borsao",
            vintage = 2021,
            region = "Campo de Borja",
            country = "España",
            grapes = listOf("Garnatxa"),
            style = WineStyle.RED,
            imageUrl = "https://images.vivino.com/thumbs/qoJL0JlITkSZRZRsYHPNVQ_375x500.jpg",
            averageRating = 4.2f,
            abv = 14.5f,
            description = "Garnatxa joven y fresco del Campo de Borja. Frutas rojas maduras, especias, taninos suaves. Excelente relación calidad-precio."
        ),
        Wine(
            id = "wine_11",
            name = "Edetaria Blanco",
            winery = "Edetaria",
            vintage = 2022,
            region = "Utiel-Requena",
            country = "España",
            grapes = listOf("Macabeo"),
            style = WineStyle.WHITE,
            imageUrl = "https://images.vivino.com/thumbs/kXpHqKZGS0OVyF2aVfGVQA_375x500.jpg",
            averageRating = 4.1f,
            abv = 12.8f,
            description = "Blanco mineral y expresivo de Utiel-Requena. Acidez viva, notas cítricas y herbáceas. Perfecto como aperitivo."
        ),
        Wine(
            id = "wine_12",
            name = "Marqués de Cáceres Rosado",
            winery = "Marqués de Cáceres",
            vintage = 2023,
            region = "Rioja",
            country = "España",
            grapes = listOf("Tempranillo", "Garnatxa"),
            style = WineStyle.ROSÉ,
            imageUrl = "https://images.vivino.com/thumbs/0sMKvIlsQU6sMqULd4Jpkw_375x500.jpg",
            averageRating = 4.0f,
            abv = 12.0f,
            description = "Rosado elegante y fresco de la Rioja. Color salmón pálido, aromas florales, sabor frutal y acidez bien definida."
        )
    )

    fun searchWines(query: String): List<Wine> {
        if (query.isBlank()) {
            return mockWines
        }
        val q = query.lowercase()
        return mockWines.filter { wine ->
            wine.name.lowercase().contains(q) ||
            wine.winery.lowercase().contains(q) ||
            wine.region.lowercase().contains(q) ||
            wine.grapes.any { it.lowercase().contains(q) }
        }
    }

    fun getWineById(id: String): Wine? {
        return mockWines.find { it.id == id }
    }
}
