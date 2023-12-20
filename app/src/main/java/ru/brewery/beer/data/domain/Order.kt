package ru.brewery.beer.data.domain

enum class Order(val value: String) {
    ASC("asc"),
    DESC("desc");

    companion object {

        fun fromValue(value: String?): Order {
            return values().firstOrNull { entry ->
                entry.value == value
            } ?: ASC
        }
    }
}