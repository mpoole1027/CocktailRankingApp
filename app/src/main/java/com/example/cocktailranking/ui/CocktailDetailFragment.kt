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

class CocktailDetailFragment : Fragment() {

    private var _binding: FragmentCocktailDetailBinding? = null
    private val binding get() = _binding!!

    private var cocktailId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cocktailId = arguments?.getString("cocktailId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCocktailDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cocktailId?.let { id ->
            RetrofitClient.apiService.getCocktailById(id).enqueue(object : Callback<CocktailResponse> {
                override fun onResponse(call: Call<CocktailResponse>, response: Response<CocktailResponse>) {
                    val drink = response.body()?.drinks?.firstOrNull()
                    if (drink != null) showDetails(drink)
                }

                override fun onFailure(call: Call<CocktailResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        }
    }

    private fun showDetails(cocktail: Cocktail) {
        binding.detailImage.load(cocktail.strDrinkThumb)
        binding.detailName.text = cocktail.strDrink
        binding.detailGlass.text = "Glass: ${cocktail.strGlass}"
        binding.detailAlcoholic.text = "Alcoholic: ${cocktail.strAlcoholic}"
        binding.detailInstructions.text = cocktail.strInstructions

        val ingredients = buildIngredientsList(cocktail)
        binding.detailIngredients.text = "Ingredients:\n$ingredients"
    }

    private fun buildIngredientsList(c: Cocktail): String {
        return (1..15).mapNotNull { i ->
            val ing = c::class.java.getDeclaredField("strIngredient$i").apply { isAccessible = true }.get(c) as? String
            val meas = c::class.java.getDeclaredField("strMeasure$i").apply { isAccessible = true }.get(c) as? String
            if (!ing.isNullOrEmpty()) "${meas.orEmpty()} $ing".trim() else null
        }.joinToString("\n")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
