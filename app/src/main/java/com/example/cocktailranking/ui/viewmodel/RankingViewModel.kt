package com.example.cocktailranking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cocktailranking.data.repository.CocktailRepository

// ViewModel for accessing top-ranked cocktails
class RankingViewModel(repository: CocktailRepository) : ViewModel()
{

    // LiveData of top cocktails from the repository
    val topCocktails = repository.topCocktails.asLiveData()
}
