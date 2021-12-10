package com.homeassignment.cocktails.ui

import androidx.lifecycle.*
import com.homeassignment.cocktails.data.model.Drink
import com.homeassignment.cocktails.di.DependencyContainer
import com.homeassignment.cocktails.utils.DataUpdate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CocktailsViewModel : ViewModel() {

    private val repo = DependencyContainer.cocktailsRepo
    private val mutableLiveData = MutableLiveData<DataUpdate<List<Drink>>>()

//    fun <T> getDrinks(s: String): LiveData<DataUpdate<T>> = liveData {
//        emit(DataUpdate.Loading())
//        try {
//            emit(DataUpdate.Success(repo.fetchDrinksFromNetwork(s)))
//        } catch (e: Exception) {
//            emit(DataUpdate.Error(e.message))
//        }
//    }

    init {
        viewModelScope.launch {
            loadDrinks("")
        }
    }

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