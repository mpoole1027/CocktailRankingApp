package com.example.cocktailranking.data.database

import androidx.room.*
import com.example.cocktailranking.data.database.model.Cocktail
import kotlinx.coroutines.flow.Flow

// Data Access Object for accessing Cocktail data in Room
@Dao
interface CocktailDao
{

    // Inserts a cocktail, replacing on primary key conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: Cocktail)

    // Gets all cocktails ordered by ELO rating (highest first)
    @Query("SELECT * FROM cocktails ORDER BY eloRating DESC")
    fun getAllCocktails(): Flow<List<Cocktail>>

    // Deletes a specific cocktail
    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)

    // Gets the top 20 cocktails by ELO rating
    @Query("SELECT * FROM cocktails ORDER BY eloRating DESC LIMIT 20")
    fun getTopCocktails(): Flow<List<Cocktail>>

    // Gets a cocktail by its API ID
    @Query("SELECT * FROM cocktails WHERE apiId = :apiId LIMIT 1")
    suspend fun getCocktailByApiId(apiId: String): Cocktail?

    // Deletes all cocktails from the table
    @Query("DELETE FROM cocktails")
    suspend fun clearAllCocktails()
}

