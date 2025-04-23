package com.example.cocktailranking.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktailranking.data.database.model.Cocktail

@Database(entities = [Cocktail::class], version = 1)
abstract class CocktailDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}
