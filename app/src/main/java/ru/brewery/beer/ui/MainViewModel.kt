package ru.brewery.beer.ui

import androidx.lifecycle.ViewModel
import ru.brewery.beer.data.repository.BeerRepository

class MainViewModel(
    private val beerRepository: BeerRepository,
): ViewModel()