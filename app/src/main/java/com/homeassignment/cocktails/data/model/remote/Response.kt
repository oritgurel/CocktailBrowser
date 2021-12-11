package com.homeassignment.cocktails.data.model.remote

import com.homeassignment.cocktails.data.model.Drink

data class Response(
	val drinks: List<Drink>? = null
)