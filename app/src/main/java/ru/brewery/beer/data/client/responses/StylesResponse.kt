package ru.brewery.beer.data.client.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.brewery.beer.data.client.models.StyleApiModel

@JsonClass(generateAdapter = true)
data class StylesResponse(
    @Json(name = ITEMS) val items: List<StyleApiModel>,
) {

    companion object {

        const val ITEMS = "items"
    }
}