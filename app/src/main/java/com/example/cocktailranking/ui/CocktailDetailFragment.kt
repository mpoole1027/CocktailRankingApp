package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.cocktailranking.databinding.FragmentCocktailDetailBinding
import com.example.cocktailranking.network.RetrofitClient
import com.example.cocktailranking.network.model.Cocktail
import com.example.cocktailranking.network.model.CocktailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Fragment to display details of a selected cocktail
class CocktailDetailFragment : Fragment()
{

    // View binding reference (nullable)
    private var _binding: FragmentCocktailDetailBinding? = null
    private val binding get() = _binding!!

    // Holds the cocktail ID passed via arguments
    private var cocktailId: String? = null

    // Retrieve cocktail ID from arguments
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        cocktailId = arguments?.getString("cocktailId")
    }

    // Inflate the layout and initialize binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Load cocktail details after view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        cocktailId?.let { id ->
            RetrofitClient.apiService.getCocktailById(id).enqueue(object : Callback<CocktailResponse>
            {

                // Show cocktail details on success
                override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>)
                {
                    val drink = response.body()?.drinks?.firstOrNull()
                    if (drink != null) showDetails(drink)
                }

                // Handle API failure
                override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {}
            })
        }
    }

    // Populate UI with cocktail information
    private fun showDetails(cocktail: Cocktail)
    {
        binding.detailImage.load(cocktail.strDrinkThumb)
        binding.detailName.text = cocktail.strDrink
        binding.detailGlass.text = "Glass: ${cocktail.strGlass}"
        binding.detailAlcoholic.text = "Alcoholic: ${cocktail.strAlcoholic}"
        binding.detailInstructions.text = cocktail.strInstructions

        // Show formatted ingredient list
        val ingredients = buildIngredientsList(cocktail)
        binding.detailIngredients.text = "$ingredients"
    }

    // Builds a string list of ingredients and measurements
    private fun buildIngredientsList(c: Cocktail): String
    {
        return (1..15).mapNotNull { i ->
            val ing = c::class.java.getDeclaredField("strIngredient$i").apply { isAccessible = true }.get(c) as? String
            val meas = c::class.java.getDeclaredField("strMeasure$i").apply { isAccessible = true }.get(c) as? String
            if (!ing.isNullOrEmpty()) "• ${meas.orEmpty()} $ing".trim() else null
        }.joinToString("\n")
    }

    // Clear binding when view is destroyed
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}

