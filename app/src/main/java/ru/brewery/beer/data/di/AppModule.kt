package ru.brewery.beer.data.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.brewery.beer.data.client.ApiClient
import ru.brewery.beer.data.client.api.BeerApi
import ru.brewery.beer.data.remote.datasource.BeerDataSource
import ru.brewery.beer.data.repository.BeerRepository
import ru.brewery.beer.data.repository.BeerRepositoryImpl
import ru.brewery.beer.ui.MainViewModel

val appModule = module {
    single<ApiClient> { ApiClient() }
    single<BeerApi> { get<ApiClient>().beerApi }
    single<BeerDataSource> { BeerDataSource(get()) }
    single<BeerRepository> { BeerRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
}