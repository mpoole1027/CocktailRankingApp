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



class CocktailRepository(
    private val dao: CocktailDao,
    private val apiService: CocktailApiService
) {
    val topCocktails: Flow<List<Cocktail>> = dao.getTopCocktails()


    suspend fun fetchCocktailById(apiId: String): com.example.cocktailranking.network.model.Cocktail? {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getCocktailById(apiId).execute()
                if (response.isSuccessful) {
                    response.body()?.drinks?.firstOrNull()
                } else {
                    Log.e("CocktailRepository", "API error: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e("CocktailRepository", "Network request failed", e)
                null
            }
        }
    }

    suspend fun insertOrUpdateCocktail(networkCocktail: com.example.cocktailranking.network.model.Cocktail) {
        val cocktail = networkCocktail.toEntity()
        dao.insertCocktail(cocktail)
    }

    suspend fun getCocktailByApiId(apiId: String): Cocktail? {
        return dao.getCocktailByApiId(apiId)
    }


    suspend fun updateElo(winner: Cocktail, loser: Cocktail) {
        val k = 32
        val expectedWinner = 1 / (1 + 10.0.pow((loser.eloRating - winner.eloRating) / 400))
        val expectedLoser = 1 / (1 + 10.0.pow((winner.eloRating - loser.eloRating) / 400))

        val updatedWinner = winner.copy(eloRating = winner.eloRating + k * (1 - expectedWinner))
        val updatedLoser = loser.copy(eloRating = loser.eloRating + k * (0 - expectedLoser))

        dao.insertCocktail(updatedWinner)
        dao.insertCocktail(updatedLoser)
    }

    suspend fun delete(cocktail: Cocktail) = dao.deleteCocktail(cocktail)
}
