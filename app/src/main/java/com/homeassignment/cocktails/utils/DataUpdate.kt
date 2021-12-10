package com.homeassignment.cocktails.utils

sealed class DataUpdate<T> {
    data class Success<T>(val data: T): DataUpdate<T>()
    data class Error<T>(val message: String?): DataUpdate<T>()
    class Loading<T>: DataUpdate<T>()
}
