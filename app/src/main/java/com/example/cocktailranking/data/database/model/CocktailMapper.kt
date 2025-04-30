package com.example.cocktailranking.data.database.model

import com.example.cocktailranking.network.model.Cocktail as NetworkCocktail

fun NetworkCocktail.toEntity(): Cocktail
{
    return Cocktail(
        // Sets the name from the network model
        name = this.strDrink,

        // Sets the API ID from the network model
        apiId = this.idDrink,

        // Sets the thumbnail URL from the network model
        thumbnailUrl = this.strDrinkThumb,

        // Sets default ELO rating
        eloRating = 1000.0
    )
}
