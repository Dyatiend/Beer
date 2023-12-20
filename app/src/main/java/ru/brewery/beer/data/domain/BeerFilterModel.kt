package ru.brewery.beer.data.domain

data class BeerFilterModel(
    val query: String? = null,
    val styles: List<Int>? = null,
    val abvMin: Double? = null,
    val abvMax: Double? = null,
    val ibuMin: Double? = null,
    val ibuMax: Double? = null,
    val isOrganic: Boolean? = null,
    val isRetired: Boolean? = null,
    val sort: Sort? = null,
    val order: Order? = null,
)