package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cocktailranking.databinding.FragmentRankingBinding
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.ui.adapter.CocktailAdapter

class RankingFragment : Fragment() {

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    // Temporary mock data that fits the new Cocktail entity model
    private val cocktailList = listOf(
        Cocktail("Margarita", "id1", "https://example.com/cocktail1.jpg", 1200.0),
        Cocktail("Mai Tai", "id2", "https://example.com/cocktail2.jpg", 1180.0),
        Cocktail("Mojito", "id3", "https://example.com/cocktail1.jpg", 1195.0),
        Cocktail("Cosmopolitan", "id4", "https://example.com/cocktail2.jpg", 1170.0),
        Cocktail("Whiskey Sour", "id5", "https://example.com/cocktail1.jpg", 1160.0),
        Cocktail("Old Fashioned", "id6", "https://example.com/cocktail2.jpg", 1150.0),
        Cocktail("Daiquiri", "id7", "https://example.com/cocktail1.jpg", 1130.0),
        Cocktail("Pina Colada", "id8", "https://example.com/cocktail2.jpg", 1125.0),
        Cocktail("Negroni", "id9", "https://example.com/cocktail1.jpg", 1110.0),
        Cocktail("Paloma", "id10", "https://example.com/cocktail2.jpg", 1105.0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rankingRecyclerView.adapter = CocktailAdapter(cocktailList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
