package com.example.cocktailranking.data.database.model

import com.google.gson.annotations.SerializedName

// DTO for parsing cocktail details from a web API response
data class CocktailDto(
    @SerializedName("idDrink") val apiId: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val thumbnailUrl: String
)
