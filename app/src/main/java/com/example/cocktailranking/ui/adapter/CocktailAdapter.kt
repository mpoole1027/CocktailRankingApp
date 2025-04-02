// CocktailAdapter.kt
package com.example.cocktailranking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailranking.data.database.model.Cocktail
import com.example.cocktailranking.databinding.ItemCocktailBinding


class CocktailAdapter(private val cocktails: List<Cocktail>) :
    RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Cocktail, position: Int) {
            binding.rankNumber.text = "${position + 1}."
            binding.cocktailName.text = cocktail.name
            binding.cocktailImage.setImageResource(cocktail.imageResId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val binding = ItemCocktailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(cocktails[position], position)
    }


    override fun getItemCount(): Int = cocktails.size
}
