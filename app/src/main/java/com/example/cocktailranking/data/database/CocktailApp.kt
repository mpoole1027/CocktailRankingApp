package com.example.cocktailranking.data.database

import android.app.Application
import androidx.room.Room
import com.example.cocktailranking.data.repository.CocktailRepository
import com.example.cocktailranking.network.RetrofitClient

class CocktailApp : Application()
{

    // Lazy initialization of the Room database
    val database: CocktailDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            CocktailDatabase::class.java,
            "cocktail_database"
        ).build()
    }

    // Lazy initialization of the repository with DAO and Retrofit service
    val repository: CocktailRepository by lazy {
        CocktailRepository(
            dao = database.cocktailDao(),
            apiService = RetrofitClient.apiService
        )
    }

    // Holds a static reference to the application instance
    companion object
    {
        lateinit var instance: CocktailApp
            private set
    }

    // Called when the application is created
    override fun onCreate()
    {
        super.onCreate()
        instance = this
    }
}
