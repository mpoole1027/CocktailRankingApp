package com.example.cocktailranking.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class Cocktail(
    // Name of the cocktail
    val name: String,

    // Unique API ID used as the primary key
    @PrimaryKey val apiId: String,

    // URL of the cocktail image
    val thumbnailUrl: String,

    // ELO rating of the cocktail
    val eloRating: Double
)