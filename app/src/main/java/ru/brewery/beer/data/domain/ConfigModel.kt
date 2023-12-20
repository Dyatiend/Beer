package ru.brewery.beer.data.domain

import ru.brewery.beer.data.client.responses.ConfigResponse

data class ConfigModel(
    val sortValues: List<Sort>,
    val orderValues: List<Order>,
    val abvMin: Double,
    val abvMax: Double,
    val ibuMin: Double,
    val ibuMax: Double,
)

fun ConfigResponse.toDomain(): ConfigModel {
    return ConfigModel(
        sortValues = sortValues.map(Sort::fromValue),
        orderValues = orderValues.map(Order::fromValue),
        abvMin = abvMin,
        abvMax = abvMax,
        ibuMin = ibuMin,
        ibuMax = ibuMax,
    )
}
