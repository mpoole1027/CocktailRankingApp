package com.example.cocktailranking.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object for creating the Retrofit client
object RetrofitClient
{

    // Base URL of the Cocktail DB API
    private const val BASE_URL = "https://www.thecocktaildb.com/"

    // Lazy-initialized API service instance
    val apiService: CocktailApiService by lazy {
        Retrofit.Builder()
            // Set the base URL
            .baseUrl(BASE_URL)
            // Use Gson for JSON parsing
            .addConverterFactory(GsonConverterFactory.create())
            // Build and create the API service interface
            .build()
            .create(CocktailApiService::class.java)
    }
}