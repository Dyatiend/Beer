package ru.brewery.beer.data.domain

import ru.brewery.beer.data.client.models.BeerApiModel

var _id: Int = 0
    get() = field++

data class BeerModel(
    val id: String,
    val name: String,
    val description: String?,
    val abv: Double?,
    val ibu: Double?,
    val isOrganic: Boolean,
    val isRetired: Boolean,
    val labels: LabelsModel,
    val style: StyleModel,
) {

    val __id: Int
        get() = _id
}

fun BeerApiModel.toDomain(): BeerModel {
    return BeerModel(
        id = id,
        name = name,
        description = description,
        abv = abv,
        ibu = ibu,
        isOrganic = isOrganic,
        isRetired = isRetired,
        labels = labels.toDomain(),
        style = style.toDomain(),
    )
}
