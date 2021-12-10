package com.homeassignment.cocktails.ui

import androidx.lifecycle.*
import com.homeassignment.cocktails.data.model.Drink
import com.homeassignment.cocktails.di.DependencyContainer
import com.homeassignment.cocktails.utils.DataUpdate
import kotlinx.coroutines.launch

class CocktailsViewModel : ViewModel() {

    private val repo = DependencyContainer.cocktailsRepo
    private val mutableLiveData = MutableLiveData<DataUpdate<List<Drink>>>()

    suspend fun loadDrinks(q: String) {
        mutableLiveData.value = DataUpdate.Loading()
        viewModelScope.launch {
            try {
                val drinks = repo.fetchDrinksFromNetwork(q)
                mutableLiveData.value = DataUpdate.Success(drinks)
            } catch (e: Exception) {
                mutableLiveData.value = DataUpdate.Error(e.message)
            }
        }
    }

    val drinksResult: LiveData<DataUpdate<List<Drink>>>
        get() = mutableLiveData

}