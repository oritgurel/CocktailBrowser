package com.homeassignment.cocktails.di

import com.homeassignment.cocktails.BuildConfig.BASE_URL
import com.homeassignment.cocktails.data.CocktailsRepository
import com.homeassignment.cocktails.data.api.ApiService
import com.homeassignment.cocktails.data.model.DrinkMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(ApiService::class.java)
    private val drinkMapper = DrinkMapper()
    val cocktailsRepo = CocktailsRepository(service, drinkMapper)
}