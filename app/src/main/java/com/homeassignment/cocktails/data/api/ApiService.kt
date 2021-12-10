package com.homeassignment.cocktails.data.api

import com.homeassignment.cocktails.data.model.remote.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
    fun getDrinks(@Query("s") s: String): Call<Response>
}