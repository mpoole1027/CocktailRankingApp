package com.example.cocktailranking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cocktailranking.data.repository.CocktailRepository

class RankingViewModel(repository: CocktailRepository) : ViewModel() {
    val topCocktails = repository.allCocktails.asLiveData()
}
