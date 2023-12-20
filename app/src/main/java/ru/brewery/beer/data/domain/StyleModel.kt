package ru.brewery.beer.data.domain

import ru.brewery.beer.data.client.models.StyleApiModel

data class StyleModel(
    val id: Int,
    val name: String,
    val description: String?,
)

fun StyleApiModel.toDomain(): StyleModel {
    return StyleModel(
        id = id,
        name = name,
        description = description,
    )
}
