package com.myassignment.mybeer.ui.beer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.myassignment.mybeer.model.Beer
import com.myassignment.mybeer.ui.beer.paging.BeerPagingSource

class BeerRepository {

    fun getBeers(): LiveData<PagingData<Beer>> {

        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
               BeerPagingSource()
            }, initialKey = 1
        ).liveData
    }
}