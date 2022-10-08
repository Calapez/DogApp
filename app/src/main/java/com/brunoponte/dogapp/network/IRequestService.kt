package com.brunoponte.dogapp.network

import com.brunoponte.dogapp.network.dtos.BreedDto
import retrofit2.http.GET
import retrofit2.http.Path
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


    @GET("breeds/{query}")
    suspend fun getBreed(
        @Path("query") query: Int,
    ) : BreedDto

    //https://api.thedogapi.com/v1/breeds?limit=100&page=0&order=Desc
    //https://api.thedogapi.com/v1/breeds/search?q=pinscher
    //https://api.thedogapi.com/v1/breeds/1
}