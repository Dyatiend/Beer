package ru.brewery.beer.data.remote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import ru.brewery.beer.data.client.models.BeerApiModel
import ru.brewery.beer.data.domain.BeerFilterModel
import ru.brewery.beer.data.domain.BeerModel
import ru.brewery.beer.data.domain.toDomain
import ru.brewery.beer.data.remote.datasource.BeerDataSource
import ru.brewery.beer.utils.Resource

class BeerPagingSource(
    private val beerDataSource: BeerDataSource,
    private val beerFilterModel: BeerFilterModel,
) : PagingSource<Int, BeerModel>() {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(BeerFilterModel::class.java)

    override fun getRefreshKey(state: PagingState<Int, BeerModel>): Int? {
        return when (val anchorPosition = state.anchorPosition) {
            null -> null
            else -> maxOf(0, anchorPosition - (state.config.initialLoadSize / 2))
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerModel> {
        val pageKey = params.key ?: 0
        val pageSize = params.loadSize

        val res = beerDataSource.fetchBeers(
            pageKey = pageKey,
            pageSize = pageSize,
            filter = adapter.toJson(beerFilterModel),
        )

        val data = when (res.status) {
            Resource.Status.SUCCESS -> {
                val beers = res.data!!

                val nextPosToLoad = pageKey + beers.items.size
                val nextKey =
                    if (beers.items.isEmpty() || beers.items.size < pageSize || nextPosToLoad >= beers.lastPageKey + 1) {
                        null
                    } else {
                        nextPosToLoad
                    }
                val prevKey =
                    if (pageKey - beers.items.size <= 0 || beers.items.isEmpty()) null else pageKey - beers.items.size

                LoadResult.Page(beers.items.map(BeerApiModel::toDomain), prevKey, nextKey)
            }

            Resource.Status.LOADING -> {
                throw IllegalStateException("Status LOADING in Resource")
            }

            Resource.Status.ERROR, Resource.Status.NETWORK_ERROR -> {
                LoadResult.Error(IllegalStateException(res.toString()))
            }
        }

        return data
    }

    val pagerFlow: Flow<PagingData<BeerModel>>
        get() {
            return Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = true,
                ),
                pagingSourceFactory = {
                    this
                }
            ).flow
        }
}