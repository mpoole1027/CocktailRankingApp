package com.example.cocktailranking.network.model

// Response wrapper for the cocktail API
data class CocktailResponse(
    // List of drinks returned by the API (nullable)
    val drinks: List<Cocktail>?
)

// Represents a cocktail from the network/API
data class Cocktail(
    // Unique ID of the drink
    val idDrink: String,

    // Name of the drink
    val strDrink: String,

    // Thumbnail image URL
    val strDrinkThumb: String,

    // Alcohol content type
    val strAlcoholic: String?,

    // Glass type used for the drink
    val strGlass: String?,

    // Preparation instructions
    val strInstructions: String?,

    // List of up to 15 ingredients (nullable)
    val strIngredient1: String?, val strIngredient2: String?, val strIngredient3: String?, val strIngredient4: String?,
    val strIngredient5: String?, val strIngredient6: String?, val strIngredient7: String?, val strIngredient8: String?,
    val strIngredient9: String?, val strIngredient10: String?, val strIngredient11: String?, val strIngredient12: String?,
    val strIngredient13: String?, val strIngredient14: String?, val strIngredient15: String?,

    // Corresponding measurements for the ingredients
    val strMeasure1: String?, val strMeasure2: String?, val strMeasure3: String?, val strMeasure4: String?,
    val strMeasure5: String?, val strMeasure6: String?, val strMeasure7: String?, val strMeasure8: String?,
    val strMeasure9: String?, val strMeasure10: String?, val strMeasure11: String?, val strMeasure12: String?,
    val strMeasure13: String?, val strMeasure14: String?, val strMeasure15: String?
)
