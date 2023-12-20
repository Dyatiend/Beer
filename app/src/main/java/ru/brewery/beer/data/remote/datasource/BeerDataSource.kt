package ru.brewery.beer.data.remote.datasource

import com.squareup.moshi.JsonDataException
import retrofit2.Response
import ru.brewery.beer.data.client.api.BeerApi
import ru.brewery.beer.data.client.responses.BeersPagingResponse
import ru.brewery.beer.data.client.responses.ConfigResponse
import ru.brewery.beer.data.client.responses.StylesResponse
import ru.brewery.beer.utils.Resource
import java.io.IOException

class BeerDataSource(
    private val beerApi: BeerApi,
) {

    private suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            return if (response.isSuccessful) {
                val body = response.body()
                Resource.success(body)
            } else {
                val errorBody = Resource.ErrorBody(response.errorBody()!!.byteString().utf8())
                Resource.error(errorBody)
            }
        } catch (e: JsonDataException) {
            e.printStackTrace()
            return Resource.error(Resource.ErrorBody(e.message ?: e.toString()))
        } catch (e: IOException) {
            e.printStackTrace()
            return Resource.networkError(Resource.ErrorBody(e.message ?: e.toString()))
        }
    }

    suspend fun fetchBeers(
        pageKey: Int? = null,
        pageSize: Int? = null,
        filter: String? = null,
    ): Resource<BeersPagingResponse> = getResult {
        beerApi.fetchBeers(
            pageKey = pageKey,
            pageSize = pageSize,
            filter = filter,
        )
    }

    suspend fun fetchStyles(): Resource<StylesResponse> = getResult {
        beerApi.fetchStyles()
    }

    suspend fun fetchConfig(): Resource<ConfigResponse> = getResult {
        beerApi.fetchConfig()
    }
}