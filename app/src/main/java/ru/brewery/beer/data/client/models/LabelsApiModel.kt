package ru.brewery.beer.data.client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LabelsApiModel(
    @Json(name = ICON) val icon: String,
    @Json(name = MEDIUM) val medium: String,
    @Json(name = LARGE) val large: String,
) {

    companion object {

        const val ICON = "icon"
        const val MEDIUM = "medium"
        const val LARGE = "large"
    }
}
