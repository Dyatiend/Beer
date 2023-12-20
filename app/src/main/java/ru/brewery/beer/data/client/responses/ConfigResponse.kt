package ru.brewery.beer.data.client.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigResponse(
    @Json(name = SORT_VALUES) val sortValues: List<String>,
    @Json(name = ORDER_VALUES) val orderValues: List<String>,
    @Json(name = ABV_MIN) val abvMin: Double,
    @Json(name = ABV_MAX) val abvMax: Double,
    @Json(name = IBU_MIN) val ibuMin: Double,
    @Json(name = IBU_MAX) val ibuMax: Double,
) {

    companion object {

        const val SORT_VALUES = "sortValues"
        const val ORDER_VALUES = "orderValues"
        const val ABV_MIN = "abvMin"
        const val ABV_MAX = "abvMax"
        const val IBU_MIN = "ibuMin"
        const val IBU_MAX = "ibuMax"
    }
}