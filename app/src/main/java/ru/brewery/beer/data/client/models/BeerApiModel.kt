package ru.brewery.beer.data.client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BeerApiModel(
    @Json(name = ID) val id: String,
    @Json(name = NAME) val name: String,
    @Json(name = DESCRIPTION) val description: String?,
    @Json(name = ABV) val abv: Double?,
    @Json(name = IBU) val ibu: Double?,
    @Json(name = IS_ORGANIC) val isOrganic: Boolean,
    @Json(name = IS_RETIRED) val isRetired: Boolean,
    @Json(name = LABELS) val labels: LabelsApiModel,
    @Json(name = STYLE) val style: StyleApiModel,
) {

    companion object {

        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val ABV = "abv"
        const val IBU = "ibu"
        const val IS_ORGANIC = "isOrganic"
        const val IS_RETIRED = "isRetired"
        const val LABELS = "labels"
        const val STYLE = "style"
    }
}