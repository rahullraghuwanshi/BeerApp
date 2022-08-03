package com.myassignment.mybeer.ui.beer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myassignment.mybeer.ui.beer.BeerRepository

class BeerViewModelProviderFactory(
    private val beerRepository: BeerRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BeerViewModel(beerRepository) as T
    }
}