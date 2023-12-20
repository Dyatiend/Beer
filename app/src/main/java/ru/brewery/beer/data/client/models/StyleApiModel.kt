package ru.brewery.beer.data.client.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StyleApiModel(
    @Json(name = ID) val id: Int,
    @Json(name = NAME) val name: String,
    @Json(name = DESCRIPTION) val description: String?,
) {

    companion object {

        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
    }
}
