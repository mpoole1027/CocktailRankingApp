package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cocktailranking.databinding.FragmentRankingBinding
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.ui.adapter.CocktailAdapter
import com.example.cocktailranking.R

class RankingFragment : Fragment()
{

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    private val cocktailList = listOf(
        Cocktail("Margarita", R.drawable.cocktail1),
        Cocktail("Mai Tai", R.drawable.cocktail2),
        Cocktail("Mojito", R.drawable.cocktail1),
        Cocktail("Cosmopolitan", R.drawable.cocktail2),
        Cocktail("Whiskey Sour", R.drawable.cocktail1),
        Cocktail("Old Fashioned", R.drawable.cocktail2),
        Cocktail("Daiquiri", R.drawable.cocktail1),
        Cocktail("Pina Colada", R.drawable.cocktail2),
        Cocktail("Negroni", R.drawable.cocktail1),
        Cocktail("Paloma", R.drawable.cocktail2),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        binding.rankingRecyclerView.adapter = CocktailAdapter(cocktailList)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}
