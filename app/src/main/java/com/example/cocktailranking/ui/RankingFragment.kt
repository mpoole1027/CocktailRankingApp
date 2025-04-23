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

class RankingFragment : Fragment() {

    private var _binding: FragmentRankingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RankingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = (requireActivity().application as CocktailApp).repository
        val factory = RankingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RankingViewModel::class.java]

        binding.rankingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.topCocktails.observe(viewLifecycleOwner) { cocktails ->
            binding.rankingRecyclerView.adapter = CocktailAdapter(cocktails) { cocktail ->
                findNavController().navigate(
                    R.id.cocktailDetailFragment,
                    bundleOf("cocktailId" to cocktail.apiId)
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
