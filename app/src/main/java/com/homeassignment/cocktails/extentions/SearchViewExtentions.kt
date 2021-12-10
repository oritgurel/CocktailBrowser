package com.homeassignment.cocktails.extentions

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
fun SearchView.asFlow() = callbackFlow {
    val afterQueryChanged: (String?) -> Unit = { query ->
        trySend(query)
    }
    val queryChangedListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(q: String?): Boolean {
            afterQueryChanged.invoke(q)
            return true
        }
    }
    setOnQueryTextListener(queryChangedListener)

    awaitClose {
        setOnQueryTextListener(null)
    }
}