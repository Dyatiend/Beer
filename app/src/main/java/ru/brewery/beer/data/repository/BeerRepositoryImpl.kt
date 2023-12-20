package ru.brewery.beer.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.brewery.beer.data.client.models.StyleApiModel
import ru.brewery.beer.data.domain.BeerFilterModel
import ru.brewery.beer.data.domain.BeerModel
import ru.brewery.beer.data.domain.ConfigModel
import ru.brewery.beer.data.domain.StyleModel
import ru.brewery.beer.data.domain.toDomain
import ru.brewery.beer.data.remote.datasource.BeerDataSource
import ru.brewery.beer.data.remote.paging.BeerPagingSource
import ru.brewery.beer.utils.Resource

class BeerRepositoryImpl(
    private val beerDataSource: BeerDataSource,
) : BeerRepository {

    override fun fetchBeers(beerFilterModel: BeerFilterModel): Flow<PagingData<BeerModel>> {
        return BeerPagingSource(
            beerDataSource,
            beerFilterModel
        ).pagerFlow
    }

    override suspend fun fetchStyles(): Resource<List<StyleModel>> {
        return withContext(Dispatchers.IO) {
            beerDataSource.fetchStyles().map { res ->
                res?.items?.map(StyleApiModel::toDomain)
            }
        }
    }

    override suspend fun fetchConfig(): Resource<ConfigModel> {
        return withContext(Dispatchers.IO) {
            beerDataSource.fetchConfig().map { res ->
                res?.toDomain()
            }
        }
    }
}