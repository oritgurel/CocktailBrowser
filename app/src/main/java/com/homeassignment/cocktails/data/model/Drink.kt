package com.homeassignment.cocktails.data.model

data class Drink(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val ingredients: List<String>
)
