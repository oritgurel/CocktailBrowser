package com.homeassignment.cocktails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.homeassignment.cocktails.data.model.Drink
import com.homeassignment.cocktails.databinding.ListItemCocktailBinding

class CocktailsAdapter: ListAdapter<Drink, CocktailItemViewHolder>(CocktailsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailItemViewHolder {
        val binding =
            ListItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object CocktailsDiffCallback: DiffUtil.ItemCallback<Drink>() {
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.id == newItem.id
        }
    }
}