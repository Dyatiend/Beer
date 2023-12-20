package ru.brewery.beer.data.client.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.brewery.beer.data.client.responses.BeersPagingResponse
import ru.brewery.beer.data.client.responses.ConfigResponse
import ru.brewery.beer.data.client.responses.StylesResponse

interface BeerApi {

    @GET("beers")
    suspend fun fetchBeers(
        @Query("pageKey") pageKey: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
        @Query("filter") filter: String? = null,
    ): Response<BeersPagingResponse>

    @GET("styles")
    suspend fun fetchStyles(): Response<StylesResponse>

    @GET("config")
    suspend fun fetchConfig(): Response<ConfigResponse>
}