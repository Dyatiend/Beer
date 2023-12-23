package ru.brewery.beer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.brewery.beer.data.domain.BeerFilterModel
import ru.brewery.beer.data.domain.ConfigModel
import ru.brewery.beer.data.domain.Order
import ru.brewery.beer.data.domain.Sort
import ru.brewery.beer.data.domain.StyleModel
import ru.brewery.beer.data.repository.BeerRepository
import ru.brewery.beer.ui.models.FilterModelFlow
import ru.brewery.beer.utils.Resource

class MainViewModel(
    private val beerRepository: BeerRepository,
): ViewModel() {

    /**
     * Все стили пива
     */
    val styles : Flow<Resource<List<StyleModel>>> = flow {
        emit(beerRepository.fetchStyles())
    }

    /**
     * Конфигурации для фильтра
     */
    val config : Flow<Resource<ConfigModel>> = flow {
        emit(beerRepository.fetchConfig())
    }

    /**
     * Фильтер
     */
    val filter : FilterModelFlow = FilterModelFlow()

    /**
     * Результат изменения фильтра
     */
    private val resultFilter = combine(
        filter.query,
        filter.styles,
        filter.abvMin,
        filter.abvMax,
        filter.ibuMin,
        filter.ibuMax,
        filter.isOrganic,
        filter.isRetired,
        filter.sort,
        filter.order
    ) {
        BeerFilterModel(
            query = it[0] as? String,
            styles = (it[1] as? List<*>)?.mapNotNull { it as? Int },
            abvMin = it[2] as? Double,
            abvMax = it[3] as? Double,
            ibuMin = it[4] as? Double,
            ibuMax = it[5] as? Double,
            isOrganic = it[6] as? Boolean,
            isRetired = it[7] as? Boolean,
            sort = it[8] as? Sort,
            order = it[9] as? Order
        )
    }

    /**
     * Результирующий список с данными (пивом)
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val resultList = resultFilter.flatMapLatest {
        beerRepository.fetchBeers(it)
    }.cachedIn(viewModelScope)

}