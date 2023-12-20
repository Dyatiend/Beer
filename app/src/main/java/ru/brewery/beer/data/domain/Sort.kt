package ru.brewery.beer.data.domain

enum class Sort(val value: String) {
    NAME("name"),
    ABV("abv"),
    IBU("ibu");

    companion object {

        fun fromValue(value: String?): Sort {
            return values().firstOrNull { entry ->
                entry.value == value
            } ?: NAME
        }
    }
}