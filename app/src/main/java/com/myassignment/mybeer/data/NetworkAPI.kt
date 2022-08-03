package com.myassignment.mybeer.data

import com.myassignment.mybeer.model.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkAPI {

    @GET("v2/beers")
    suspend fun getBeers(
        @Query("page") pageNumber: Int = 1,
        @Query("per_page") pageSize: Int = 25,
    ): Response<List<Beer>>

}