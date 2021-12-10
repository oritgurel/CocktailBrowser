package com.homeassignment.cocktails.data.model

import android.util.Log
import com.homeassignment.cocktails.data.model.remote.DrinksItem
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

class DrinkMapper {

    private val TAG = this::class.simpleName

    fun createDrink(drinkResponse: DrinksItem): Drink {
        val ingredients = mutableListOf<String>()
        try {
            for (prop in DrinksItem::class.members) {
                if (prop.name.startsWith("strIngredient")) {
                    val ing = prop as KProperty1<*, *>
                    (ing.getter.call(drinkResponse) as? String)?.let { ingredients.add(it) }
                }
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(TAG, it) } ?: print(e.stackTrace)
        }

        return Drink(
            id = drinkResponse.idDrink ?: "",
            name = drinkResponse.strDrink ?: "",
            imageUrl = drinkResponse.strDrinkThumb,
            ingredients = ingredients
        )
    }
}