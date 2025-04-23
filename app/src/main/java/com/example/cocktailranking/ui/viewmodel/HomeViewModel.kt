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
import android.util.Log


class HomeViewModel : ViewModel() {
    private val _cocktails = MutableLiveData<List<Cocktail>>()
    val cocktails: LiveData<List<Cocktail>> get() = _cocktails

    private val cocktailQueue = ArrayDeque<Cocktail>()
    private val desiredQueueSize = 4

    fun fetchInitialQueue() {
        // Only fill if needed
        if (cocktailQueue.size < desiredQueueSize) {
            val cocktailsToFetch = desiredQueueSize - cocktailQueue.size
            repeat(cocktailsToFetch) {
                fetchOneRandomCocktail()
            }
        }

        // Show first 2 if not already shown
        if (_cocktails.value == null && cocktailQueue.size >= 2) {
            showNextPair()
        }
    }

    fun resetForVoting() {
        if (cocktailQueue.size >= 2) {
            showNextPair()
        } else {
            _cocktails.postValue(emptyList()) // Optional: show loading state
        }

        refillQueueIfNeeded()
    }

    private fun showNextPair() {
        if (cocktailQueue.size >= 2) {
            val nextPair = listOf(cocktailQueue.removeFirst(), cocktailQueue.removeFirst())
            _cocktails.postValue(nextPair)
        } else {
            _cocktails.postValue(emptyList()) // fallback, should rarely happen
        }
    }

    private fun refillQueueIfNeeded() {
        val cocktailsToFetch = desiredQueueSize - cocktailQueue.size
        repeat(cocktailsToFetch) {
            fetchOneRandomCocktail()
        }
    }

    private fun fetchOneRandomCocktail() {
        RetrofitClient.apiService.getRandomCocktail().enqueue(object : Callback<CocktailResponse> {
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                response.body()?.drinks?.firstOrNull()?.let { newCocktail ->
                    if (cocktailQueue.none { it.idDrink == newCocktail.idDrink }) {
                        cocktailQueue.addLast(newCocktail)

                        Log.d("Queue", "Cocktail added: ${newCocktail.strDrink}. Queue size: ${cocktailQueue.size}")

                        if (_cocktails.value.isNullOrEmpty() && cocktailQueue.size >= 2) {
                            Log.d("Queue", "Calling showNextPair() after preload")
                            showNextPair()
                        }
                    } else {
                        fetchOneRandomCocktail()
                    }
                }
            }

            override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                Log.e("CocktailFetch", "Failed to fetch cocktail: ${t.message}")
            }
        })
    }



}

