package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailranking.R
import com.example.cocktailranking.databinding.FragmentSearchBinding
import com.example.cocktailranking.ui.adapter.SearchAdapter
import com.example.cocktailranking.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        adapter = SearchAdapter { cocktail ->
            findNavController().navigate(
                R.id.cocktailDetailFragment,
                bundleOf("cocktailId" to cocktail.idDrink)
            )
        }

        binding.searchResultsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultsRecycler.adapter = adapter

        binding.searchInput.apply {
            isIconified = false
            clearFocus()
            isFocusable = true
            isFocusableInTouchMode = true
            isClickable = true
            setOnClickListener {
                isIconified = false
                requestFocus()
            }
            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (!hasFocus) clearFocus()
            }

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.searchCocktails(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        viewModel.results.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
