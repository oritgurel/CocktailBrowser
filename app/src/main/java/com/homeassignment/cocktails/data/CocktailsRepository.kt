package com.homeassignment.cocktails.data

import com.homeassignment.cocktails.data.api.ApiService
import com.homeassignment.cocktails.data.model.Drink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CocktailsRepository(private val apiService: ApiService) {

    suspend fun fetchDrinksFromNetwork(query: String): List<Drink> {
        return withContext(Dispatchers.IO) {
               apiService.getDrinks(query).execute().body()?.drinks ?: listOf()
        }
    }
}