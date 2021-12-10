package com.homeassignment.cocktails.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.homeassignment.cocktails.data.model.Drink
import com.homeassignment.cocktails.databinding.ListItemCocktailBinding

class CocktailItemViewHolder(private val binding: ListItemCocktailBinding): RecyclerView.ViewHolder(binding.root) {
    private val image: ImageView = binding.liCocktailImage
    private val name: TextView = binding.liCocktailName
    private val ingredients: TextView = binding.liCocktailIngredients

    init {
        binding.root.setOnClickListener {
            ingredients.isSelected = true
        }
    }

    fun bind(drink: Drink) {
        Glide.with(binding.root.context).load(drink.imageUrl).into(image)
        name.text = drink.name
        ingredients.apply {
            text = drink.ingredients.joinToString(", ")
        }
    }

}