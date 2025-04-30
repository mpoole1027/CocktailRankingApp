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

// Fragment for searching and displaying cocktails from the API
class SearchFragment : Fragment()
{

    // View binding for fragment_search.xml
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // ViewModel and adapter references
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter

    // Inflate the layout and initialize binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Set up ViewModel, adapter, search input, and observers
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        // Set up adapter with click listener to navigate to detail view
        adapter = SearchAdapter { cocktail ->
            findNavController().navigate(
                R.id.cocktailDetailFragment,
                bundleOf("cocktailId" to cocktail.idDrink)
            )
        }

        // Set RecyclerView layout and adapter
        binding.searchResultsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.searchResultsRecycler.adapter = adapter

        // Configure search input behavior
        binding.searchInput.apply {
            isIconified = false
            clearFocus()
            isFocusable = true
            isFocusableInTouchMode = true
            isClickable = true

            // Keep focus on search field when clicked
            setOnClickListener {
                isIconified = false
                requestFocus()
            }

            // Remove focus when input is blurred
            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (!hasFocus) clearFocus()
            }

            // Handle search submit
            setOnQueryTextListener(object : SearchView.OnQueryTextListener
            {
                override fun onQueryTextSubmit(query: String?): Boolean
                {
                    query?.let { viewModel.searchCocktails(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }

        // Observe search results and update list
        viewModel.results.observe(viewLifecycleOwner)
        {
            adapter.submitList(it)
        }
    }

    // Clear binding to avoid memory leaks
    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}

