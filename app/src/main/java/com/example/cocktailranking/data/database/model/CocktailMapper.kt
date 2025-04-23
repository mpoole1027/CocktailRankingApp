package com.example.cocktailranking.data.database.model

import com.example.cocktailranking.network.model.Cocktail as NetworkCocktail

fun NetworkCocktail.toEntity(): Cocktail {
    return Cocktail(
        name = this.strDrink,
        apiId = this.idDrink,
        thumbnailUrl = this.strDrinkThumb,
        eloRating = 1000.0 // default rank
    )
}
