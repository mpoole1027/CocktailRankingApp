package com.example.cocktailranking.data.repository

import android.util.Log
import com.example.cocktailranking.data.database.CocktailDao
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.data.database.model.toEntity
import com.example.cocktailranking.network.CocktailApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.math.pow


// Repository class to manage data operations from Room and API
class CocktailRepository(
    private val dao: CocktailDao,
    private val apiService: CocktailApiService
)
{
    // Stream of all cocktails from the local database
    val topCocktails: Flow<List<Cocktail>> = dao.getTopCocktails()

    // Fetches a cocktail from the API by its ID
    suspend fun fetchCocktailById(apiId: String): com.example.cocktailranking.network.model.Cocktail? {
        return withContext(Dispatchers.IO)
        {
            try
            {
                val response = apiService.getCocktailById(apiId).execute()
                if (response.isSuccessful)
                {
                    // Return first cocktail in the response
                    response.body()?.drinks?.firstOrNull()
                } else
                {
                    // Log API error and return null
                    Log.e("CocktailRepository", "API error: ${response.code()}")
                    null
                }
            } catch (e: Exception)
            {
                // Log network error and return null
                Log.e("CocktailRepository", "Network request failed", e)
                null
            }
        }
    }

    // Converts and inserts or updates a cocktail in the database
    suspend fun insertOrUpdateCocktail(networkCocktail: com.example.cocktailranking.network.model.Cocktail) {
        val existing = dao.getCocktailByApiId(networkCocktail.idDrink)
        if (existing == null)
        {
            val cocktail = networkCocktail.toEntity()
            dao.insertCocktail(cocktail)
        } else
        {
            // Skip reinsertion so ELO isn't reset
            Log.d("CocktailRepository", "Cocktail ${existing.name} already in DB with ELO ${existing.eloRating}")
        }
    }

    // Clears all cocktails from the database
    suspend fun clearAll()
    {
        dao.clearAllCocktails()
    }

    // Retrieves a cocktail by its API ID
    suspend fun getCocktailByApiId(apiId: String): Cocktail?
    {
        return dao.getCocktailByApiId(apiId)
    }

    // Updates ELO rating for winner and loser, then saves changes
    suspend fun updateElo(winner: Cocktail, loser: Cocktail)
    {
        val k = 32
        val expectedWinner = 1 / (1 + 10.0.pow((loser.eloRating - winner.eloRating) / 400))
        val expectedLoser = 1 / (1 + 10.0.pow((winner.eloRating - loser.eloRating) / 400))

        val updatedWinner = winner.copy(eloRating = winner.eloRating + k * (1 - expectedWinner))
        val updatedLoser = loser.copy(eloRating = loser.eloRating + k * (0 - expectedLoser))

        dao.insertCocktail(updatedWinner)
        dao.insertCocktail(updatedLoser)
    }

    // Deletes a cocktail from the database
    suspend fun delete(cocktail: Cocktail) = dao.deleteCocktail(cocktail)
}
