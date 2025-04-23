package com.example.cocktailranking.data.database

import android.app.Application
import androidx.room.Room

class CocktailApp : Application() {
    // Lazily initialize the database instance
    val database: CocktailDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            CocktailDatabase::class.java,
            "cocktail_database"
        ).build()
    }
}
