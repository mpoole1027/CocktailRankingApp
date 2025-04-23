package com.example.cocktailranking.network
import com.example.cocktailranking.network.model.CocktailResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("api/json/v1/1/filter.php")
    fun getCocktailsByCategory(@Query("c") category: String): Call<CocktailResponse>

    @GET("api/json/v1/1/search.php")
    fun searchCocktails(@Query("s") name: String): Call<CocktailResponse>

    @GET("api/json/v1/1/random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    @GET("api/json/v1/1/lookup.php")
    fun getCocktailById(@Query("i") id: String): Call<CocktailResponse>

}