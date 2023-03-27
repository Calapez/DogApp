package com.brunoponte.dogapp.network

import com.brunoponte.dogapp.network.models.BreedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface IRequestService {

    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") pageSize: Int,
        @Query("page") page: Int,
        @Query("order") order: String,
    ) : List<BreedDto>

    @GET("breeds/search")
    suspend fun searchBreeds(
        @Query("q") query: String,
    ) : List<BreedDto>

}