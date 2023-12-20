package ru.brewery.beer.data.domain

import ru.brewery.beer.data.client.models.LabelsApiModel

data class LabelsModel(
    val icon: String,
    val medium: String,
    val large: String,
)

fun LabelsApiModel.toDomain(): LabelsModel {
    return LabelsModel(
        icon = icon,
        medium = medium,
        large = large,
    )
}
