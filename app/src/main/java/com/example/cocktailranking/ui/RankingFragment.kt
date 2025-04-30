package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailranking.R
import com.example.cocktailranking.databinding.FragmentRankingBinding
import com.example.cocktailranking.ui.adapter.CocktailAdapter
import com.example.cocktailranking.viewmodel.RankingViewModel
import com.example.cocktailranking.viewmodel.RankingViewModelFactory
import com.example.cocktailranking.data.database.CocktailApp

// Fragment to display top-ranked cocktails in a RecyclerView
class RankingFragment : Fragment()
{

    // View binding for fragment_ranking.xml
    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    // ViewModel to access top cocktail data
    private lateinit var viewModel: RankingViewModel

    // Inflate layout and initialize binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Set up ViewModel and RecyclerView after view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        // Initialize repository and ViewModel with factory
        val repository = (requireActivity().application as CocktailApp).repository
        val factory = RankingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RankingViewModel::class.java]

        // Set RecyclerView layout to vertical list
        binding.rankingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe top cocktails and populate RecyclerView with adapter
        viewModel.topCocktails.observe(viewLifecycleOwner)
        { cocktails ->
            binding.rankingRecyclerView.adapter = CocktailAdapter(cocktails) { cocktail ->
                // Navigate to detail screen on item click
                findNavController().navigate(
                    R.id.cocktailDetailFragment,
                    bundleOf("cocktailId" to cocktail.apiId)
                )
            }
        }
    }

    // Clear binding reference to prevent memory leaks
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}

