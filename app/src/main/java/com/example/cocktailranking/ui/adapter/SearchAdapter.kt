package com.example.cocktailranking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktailranking.databinding.ItemSearchResultBinding
import com.example.cocktailranking.network.model.Cocktail

// RecyclerView Adapter for displaying search results
class SearchAdapter(
    private val onItemClick: (Cocktail) -> Unit // Callback for item click
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>()
{

    // List of cocktails to display
    private var items: List<Cocktail> = emptyList()

    // Updates the list and refreshes the view
    fun submitList(list: List<Cocktail>)
    {
        items = list
        notifyDataSetChanged()
    }

    // ViewHolder for each search result item
    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root)
    {

        // Binds cocktail data to the view
        fun bind(cocktail: Cocktail)
        {
            binding.cocktailName.text = cocktail.strDrink
            binding.cocktailImage.load(cocktail.strDrinkThumb)

            // Handle item click
            binding.root.setOnClickListener {
                onItemClick(cocktail)
            }
        }
    }

    // Creates a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder
    {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int)
    {
        holder.bind(items[position])
    }

    // Returns total number of items
    override fun getItemCount(): Int = items.size
}