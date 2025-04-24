package com.example.cocktailranking.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cocktailranking.databinding.ItemSearchResultBinding
import com.example.cocktailranking.network.model.Cocktail

class SearchAdapter(
    private val onItemClick: (Cocktail) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var items: List<Cocktail> = emptyList()

    fun submitList(list: List<Cocktail>) {
        items = list
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: Cocktail) {
            binding.cocktailName.text = cocktail.strDrink
            binding.cocktailImage.load(cocktail.strDrinkThumb)

            binding.root.setOnClickListener {
                onItemClick(cocktail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
