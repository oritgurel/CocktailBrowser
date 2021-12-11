package com.homeassignment.cocktails.data.model.remote

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.homeassignment.cocktails.BuildConfig.*
import com.homeassignment.cocktails.data.model.Drink

class DrinkTypeAdapter: TypeAdapter<Drink>() {

    override fun write(out: JsonWriter?, value: Drink?) {
        throw UnsupportedOperationException()
    }

    override fun read(`in`: JsonReader): Drink {
        var id = ""
        var name = ""
        var imageUrl = ""
        val ingredients = mutableListOf<String>()

        `in`.beginObject()
        var fieldName: String? = null
        while (`in`.hasNext()) {
            if (`in`.peek() == JsonToken.NAME) {
                fieldName = `in`.nextName()
            } else `in`.skipValue()

            when (fieldName) {
                D_ID_PROP_NAME -> { id = `in`.nextString();  }
                D_NAME_PROP_NAME -> { name = `in`.nextString() }
                D_IMAGE_PROP_NAME -> { imageUrl = `in`.nextString() }
                else -> {
                    if (fieldName?.startsWith(D_INGREDIENT_PROP_NAME_PREFIX) == true) {
                        if (`in`.peek() == JsonToken.STRING) {
                            ingredients.add(`in`.nextString())
                        }
                    }
                }
            }
        }
        `in`.endObject()
        return Drink(id, name, imageUrl, ingredients)
    }
}