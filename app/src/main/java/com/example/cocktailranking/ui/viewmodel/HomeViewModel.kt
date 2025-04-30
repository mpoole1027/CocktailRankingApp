package com.example.cocktailranking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cocktailranking.network.RetrofitClient
import com.example.cocktailranking.network.model.Cocktail as NetworkCocktail
import com.example.cocktailranking.network.model.CocktailResponse
import com.example.cocktailranking.data.repository.CocktailRepository
import com.example.cocktailranking.data.database.model.Cocktail as DbCocktail
import com.example.cocktailranking.data.database.model.toEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel for handling cocktail voting logic with ELO updates and queueing
class HomeViewModel(private val repository: CocktailRepository) : ViewModel()
{

    // LiveData to expose a pair of cocktails to vote on
    private val _cocktails = MutableLiveData<List<NetworkCocktail>>()
    val cocktails: LiveData<List<NetworkCocktail>> get() = _cocktails

    // LiveData of top cocktails by ELO from database
    val topCocktails: LiveData<List<DbCocktail>> = repository.topCocktails.asLiveData()

    // Queue to store random cocktails for voting
    private val cocktailQueue = ArrayDeque<NetworkCocktail>()
    private val desiredQueueSize = 4

    // Fills the queue and shows first pair if needed
    fun fetchInitialQueue()
    {
        if (cocktailQueue.size < desiredQueueSize)
        {
            val cocktailsToFetch = desiredQueueSize - cocktailQueue.size
            repeat(cocktailsToFetch)
            {
                fetchOneRandomCocktail()
            }
        }

        // Show pair if nothing shown yet and enough in queue
        if (_cocktails.value == null && cocktailQueue.size >= 2)
        {
            showNextPair()
        }
    }

    // Resets UI for next vote and refills queue if needed
    fun resetForVoting()
    {
        if (cocktailQueue.size >= 2)
        {
            showNextPair()
        } else
        {
            _cocktails.postValue(emptyList())
        }

        refillQueueIfNeeded()
    }

    // Handles voting and updates ELO ratings
    fun vote(winner: NetworkCocktail, loser: NetworkCocktail)
    {
        CoroutineScope(Dispatchers.IO).launch{
            try
            {
                val winnerFromDb = repository.getCocktailByApiId(winner.idDrink)
                val loserFromDb = repository.getCocktailByApiId(loser.idDrink)

                if (winnerFromDb != null && loserFromDb != null)
                {
                    val oldWinnerElo = winnerFromDb.eloRating
                    val oldLoserElo = loserFromDb.eloRating

                    // Update ratings in the database
                    repository.updateElo(winnerFromDb, loserFromDb)

                    val updatedWinner = repository.getCocktailByApiId(winner.idDrink)
                    val updatedLoser = repository.getCocktailByApiId(loser.idDrink)

                    Log.d("ELO_UPDATE", "${winner.strDrink}: $oldWinnerElo → ${updatedWinner?.eloRating}")
                    Log.d("ELO_UPDATE", "${loser.strDrink}: $oldLoserElo → ${updatedLoser?.eloRating}")
                } else
                {
                    Log.e("HomeViewModel", "One or both cocktails not found in DB")
                }
            } catch (e: Exception)
            {
                Log.e("HomeViewModel", "Failed to update ELO: ${e.message}")
            }
        }
    }

    // Displays the next two cocktails from the queue
    private fun showNextPair()
    {
        if (cocktailQueue.size >= 2)
        {
            val nextPair = listOf(cocktailQueue.removeFirst(), cocktailQueue.removeFirst())
            _cocktails.postValue(nextPair)
        } else
        {
            _cocktails.postValue(emptyList())
        }
    }

    // Refills queue to maintain desired number of cocktails
    private fun refillQueueIfNeeded()
    {
        val cocktailsToFetch = desiredQueueSize - cocktailQueue.size
        repeat(cocktailsToFetch)
        {
            fetchOneRandomCocktail()
        }
    }

    // Fetches a single random cocktail and adds it to the queue
    private fun fetchOneRandomCocktail()
    {
        RetrofitClient.apiService.getRandomCocktail().enqueue(object : Callback<CocktailResponse> {

            // Handles successful API response
            override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>)
            {
                response.body()?.drinks?.firstOrNull()?.let { newCocktail ->

                    // Save cocktail to local DB
                    CoroutineScope(Dispatchers.IO).launch {
                        try
                        {
                            repository.insertOrUpdateCocktail(newCocktail)
                        } catch (e: Exception)
                        {
                            Log.e("HomeViewModel", "Failed to save to DB: ${e.message}")
                        }
                    }

                    // Add to queue if it's not a duplicate
                    if (cocktailQueue.none { it.idDrink == newCocktail.idDrink })
                    {
                        cocktailQueue.addLast(newCocktail)
                        Log.d("Queue", "Cocktail added: ${newCocktail.strDrink}. Queue size: ${cocktailQueue.size}")

                        // Show pair if needed
                        if (_cocktails.value.isNullOrEmpty() && cocktailQueue.size >= 2)
                        {
                            Log.d("Queue", "Calling showNextPair() after preload")
                            showNextPair()
                        }
                    } else
                    {
                        // Fetch again if duplicate
                        fetchOneRandomCocktail()
                    }
                }
            }

            // Handles API failure
            override fun onFailure(call: Call<CocktailResponse>, t: Throwable)
            {
                Log.e("CocktailFetch", "Failed to fetch cocktail: ${t.message}")
            }
        })
    }
}

