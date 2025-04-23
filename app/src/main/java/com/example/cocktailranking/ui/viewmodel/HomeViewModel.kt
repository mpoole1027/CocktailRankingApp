package com.example.cocktailranking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktailranking.network.RetrofitClient
import com.example.cocktailranking.network.model.Cocktail
import com.example.cocktailranking.network.model.CocktailResponse
import com.example.cocktailranking.data.repository.CocktailRepository
import com.example.cocktailranking.data.database.model.toEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CocktailRepository) : ViewModel() {

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

    fun vote(winner: com.example.cocktailranking.network.model.Cocktail, loser: com.example.cocktailranking.network.model.Cocktail) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val winnerEntity = winner.toEntity()
                val loserEntity = loser.toEntity()
                repository.updateElo(winnerEntity, loserEntity)
                Log.d("HomeViewModel", "ELO updated for ${winner.strDrink} vs ${loser.strDrink}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to update ELO: ${e.message}")
            }
        }
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

                    // Save to Room (on IO thread)
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            repository.insertOrUpdateCocktail(newCocktail)
                        } catch (e: Exception) {
                            Log.e("HomeViewModel", "Failed to save to DB: ${e.message}")
                        }
                    }

                    // Enqueue if not already in the queue
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
