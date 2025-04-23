package com.example.cocktailranking.data.database

import androidx.room.*
import com.example.cocktailranking.data.database.model.Cocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktail(cocktail: Cocktail)

    @Query("SELECT * FROM cocktails ORDER BY eloRating DESC")
    fun getAllCocktails(): Flow<List<Cocktail>>

    @Delete
    suspend fun deleteCocktail(cocktail: Cocktail)
}
