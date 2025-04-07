package com.example.cocktailranking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.databinding.ItemCocktailBinding

// Adapter for displaying cocktails in a RecyclerView
class CocktailAdapter(private val cocktails: List<Cocktail>) :
    RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>()
{

    // ViewHolder for binding cocktail data to the item layout
    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
        // Bind cocktail details to the UI elements
        fun bind(cocktail: Cocktail, position: Int)
        {
            binding.rankNumber.text = "${position + 1}."
            binding.cocktailName.text = cocktail.name
            binding.cocktailImage.setImageResource(cocktail.imageResId)
        }
    }

    // Inflate the item layout and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder
    {
        val binding = ItemCocktailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CocktailViewHolder(binding)
    }

    // Bind data to the ViewHolder at the given position
    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int)
    {
        holder.bind(cocktails[position], position)
    }

    // Return the total number of cocktail items
    override fun getItemCount(): Int = cocktails.size
}
