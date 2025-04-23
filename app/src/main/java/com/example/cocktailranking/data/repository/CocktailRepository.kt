package com.example.cocktailranking.data.repository

import com.example.cocktailranking.network.CocktailApiService
import com.example.cocktailranking.data.database.CocktailDao
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.data.database.model.CocktailDto
import kotlinx.coroutines.flow.Flow
import kotlin.math.pow

class CocktailRepository(
    private val dao: CocktailDao,
    private val apiService: CocktailApiService
) {
    val allCocktails: Flow<List<Cocktail>> = dao.getAllCocktails()

    suspend fun fetchCocktailById(apiId: String): CocktailDto {
        return apiService.getCocktailById(apiId).drinks.first()
    }

    suspend fun insertOrUpdateCocktail(dto: CocktailDto) {
        val cocktail = Cocktail(
            name = dto.name,
            apiId = dto.apiId,
            thumbnailUrl = dto.thumbnailUrl,
            eloRating = 1000.0 // Default initial rating
        )
        dao.insertCocktail(cocktail)
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
