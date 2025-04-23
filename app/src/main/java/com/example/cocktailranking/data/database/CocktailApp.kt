package com.example.cocktailranking.data.database

import android.app.Application
import androidx.room.Room
import com.example.cocktailranking.data.repository.CocktailRepository
import com.example.cocktailranking.network.RetrofitClient

class CocktailApp : Application() {

    val database: CocktailDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            CocktailDatabase::class.java,
            "cocktail_database"
        ).build()
    }

    // Expose the repository using Room + Retrofit
    val repository: CocktailRepository by lazy {
        CocktailRepository(
            dao = database.cocktailDao(),
            apiService = RetrofitClient.apiService
        )
    }

    companion object {
        lateinit var instance: CocktailApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
