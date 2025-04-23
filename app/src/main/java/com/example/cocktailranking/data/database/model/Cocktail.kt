package com.example.cocktailranking.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class Cocktail(
    val name: String,
    @PrimaryKey val apiId: String,  // Primary key must be unique
    val thumbnailUrl: String,
    val eloRating: Double
)