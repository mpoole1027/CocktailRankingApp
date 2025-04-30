package com.example.cocktailranking.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktailranking.data.database.model.Cocktail

// Defines the Room database with the Cocktail entity and version 1
@Database(entities = [Cocktail::class], version = 1)
abstract class CocktailDatabase : RoomDatabase()
{

    // Provides access to DAO functions
    abstract fun cocktailDao(): CocktailDao
}
