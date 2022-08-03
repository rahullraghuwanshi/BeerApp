package com.myassignment.mybeer.ui.beer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myassignment.mybeer.data.NetworkInstance
import com.myassignment.mybeer.model.Beer

class BeerPagingSource: PagingSource<Int, Beer>() {
    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        return try {
            val position = params.key ?: 1
            val response = NetworkInstance.api.getBeers()
            LoadResult.Page(
                data = response.body()!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}