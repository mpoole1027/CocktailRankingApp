package com.example.cocktailranking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailranking.network.RetrofitClient
import com.example.cocktailranking.network.model.Cocktail
import com.example.cocktailranking.network.model.CocktailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ViewModel for searching cocktails using the API
class SearchViewModel : ViewModel()
{

    // Backing field for search results
    private val _results = MutableLiveData<List<Cocktail>>()

    // Exposes search results as LiveData
    val results: LiveData<List<Cocktail>> get() = _results

    // Performs a cocktail search by name
    fun searchCocktails(query: String)
    {
        RetrofitClient.apiService.searchCocktails(query).enqueue(object : Callback<CocktailResponse>
        {

            // Handles successful API response
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>)
            {
                _results.postValue(response.body()?.drinks ?: emptyList())
            }

            // Handles API request failure
            override fun onFailure(call: Call<CocktailResponse>, t: Throwable)
            {
                _results.postValue(emptyList())
            }
        })
    }
}

