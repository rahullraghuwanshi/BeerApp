package com.myassignment.mybeer.ui.beer.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myassignment.mybeer.model.Beer
import com.myassignment.mybeer.ui.beer.BeerRepository

class BeerViewModel(
    private val beerRepository: BeerRepository
) : ViewModel() {


    fun getBeer(): LiveData<PagingData<Beer>> {
        return beerRepository.getBeers().cachedIn(viewModelScope)
    }
}