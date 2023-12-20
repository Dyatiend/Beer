package ru.brewery.beer.data.client.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.brewery.beer.data.client.models.BeerApiModel

@JsonClass(generateAdapter = true)
data class BeersPagingResponse(
    @Json(name = ITEMS) val items: List<BeerApiModel>,
    @Json(name = FIRST_PAGE_KEY) val firstPageKey: Int,
    @Json(name = LAST_PAGE_KEY) val lastPageKey: Int,
) {

    companion object {

        const val ITEMS = "items"
        const val FIRST_PAGE_KEY = "firstPageKey"
        const val LAST_PAGE_KEY = "lastPageKey"
    }
}