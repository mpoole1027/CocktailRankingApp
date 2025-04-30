package com.example.cocktailranking.network
import com.example.cocktailranking.network.model.CocktailResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Defines API endpoints for fetching cocktail data
interface CocktailApiService
{
    // Gets cocktails filtered by category
    @GET("api/json/v1/1/filter.php")
    fun getCocktailsByCategory(@Query("c") category: String): Call<CocktailResponse>

    // Searches cocktails by name
    @GET("api/json/v1/1/search.php")
    fun searchCocktails(@Query("s") name: String): Call<CocktailResponse>

    // Fetches a random cocktail
    @GET("api/json/v1/1/random.php")
    fun getRandomCocktail(): Call<CocktailResponse>

    // Looks up a cocktail by its ID
    @GET("api/json/v1/1/lookup.php")
    fun getCocktailById(@Query("i") id: String): Call<CocktailResponse>
}