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

class SearchViewModel : ViewModel() {

    private val _results = MutableLiveData<List<Cocktail>>()
    val results: LiveData<List<Cocktail>> get() = _results

    fun searchCocktails(query: String) {
        RetrofitClient.apiService.searchCocktails(query).enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                _results.postValue(response.body()?.drinks ?: emptyList())
            }

            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                _results.postValue(emptyList())
            }
        })
    }
}
