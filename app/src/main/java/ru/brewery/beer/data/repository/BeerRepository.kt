package ru.brewery.beer.data.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.brewery.beer.data.domain.BeerFilterModel
import ru.brewery.beer.data.domain.BeerModel
import ru.brewery.beer.data.domain.ConfigModel
import ru.brewery.beer.data.domain.StyleModel
import ru.brewery.beer.utils.Resource

interface BeerRepository {

    fun fetchBeers(beerFilterModel: BeerFilterModel): Flow<PagingData<BeerModel>>

    suspend fun fetchStyles(): Resource<List<StyleModel>>

    suspend fun fetchConfig(): Resource<ConfigModel>
}