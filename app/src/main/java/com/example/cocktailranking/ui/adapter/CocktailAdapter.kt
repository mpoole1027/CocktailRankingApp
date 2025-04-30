package com.example.cocktailranking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.databinding.ItemCocktailBinding

// RecyclerView Adapter to display cocktails with click handling
class CocktailAdapter(
    private val cocktails: List<Cocktail>,
    private val onItemClick: (Cocktail) -> Unit // Callback when item is clicked
) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>()
{

    // ViewHolder that binds cocktail data to the view
    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root)
    {

        // Binds data to the UI components
        fun bind(cocktail: Cocktail, position: Int)
        {
            // Set ranking number
            binding.rankNumber.text = "${position + 1}."

            // Display ELO rating
            binding.eloTextView.text = "ELO: ${cocktail.eloRating.toInt()}"

            // Display cocktail name
            binding.cocktailName.text = cocktail.name

            // Load image using Coil
            binding.cocktailImage.load(cocktail.thumbnailUrl)

            // Handle item click
            binding.root.setOnClickListener {
                onItemClick(cocktail)
            }
        }
    }

    // Creates and returns a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder
    {
        val binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int)
    {
        holder.bind(cocktails[position], position)
    }

    // Returns total number of items
    override fun getItemCount(): Int = cocktails.size
}

