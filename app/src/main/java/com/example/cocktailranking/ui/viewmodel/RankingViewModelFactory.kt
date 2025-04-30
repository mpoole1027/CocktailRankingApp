package com.example.cocktailranking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailranking.data.repository.CocktailRepository

// Factory class for creating RankingViewModel instances
class RankingViewModelFactory(
    private val repository: CocktailRepository
) : ViewModelProvider.Factory
{

    // Creates an instance of RankingViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(RankingViewModel::class.java))
        {
            return RankingViewModel(repository) as T
        }
        // Throw error if ViewModel type is not supported
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

