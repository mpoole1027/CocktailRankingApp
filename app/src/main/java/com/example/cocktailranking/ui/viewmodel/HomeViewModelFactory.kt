package com.example.cocktailranking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailranking.data.repository.CocktailRepository

// Factory class to create HomeViewModel instances with a repository parameter
class HomeViewModelFactory(
    private val repository: CocktailRepository
) : ViewModelProvider.Factory
{

    // Creates an instance of HomeViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
        {
            return HomeViewModel(repository) as T
        }
        // Throw error if ViewModel type is not supported
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

