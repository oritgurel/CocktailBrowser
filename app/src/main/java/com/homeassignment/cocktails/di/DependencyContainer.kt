package com.homeassignment.cocktails.di

import com.google.gson.GsonBuilder
import com.homeassignment.cocktails.BuildConfig.BASE_URL
import com.homeassignment.cocktails.data.CocktailsRepository
import com.homeassignment.cocktails.data.api.ApiService
import com.homeassignment.cocktails.data.model.Drink
import com.homeassignment.cocktails.data.model.remote.DrinkTypeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyContainer {

    private val gson = GsonBuilder()
        .registerTypeAdapter(Drink::class.java, DrinkTypeAdapter())
        .create()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    private val service = retrofit.create(ApiService::class.java)
    val cocktailsRepo = CocktailsRepository(service)
}