package com.homeassignment.cocktails.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.homeassignment.cocktails.R
import com.homeassignment.cocktails.viewmodels.CocktailsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CocktailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        showCocktailsFragment()
    }

    private fun showCocktailsFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.fragment_container, CocktailsFragment.newInstance()
        ).commit()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[CocktailsViewModel::class.java]
    }
}