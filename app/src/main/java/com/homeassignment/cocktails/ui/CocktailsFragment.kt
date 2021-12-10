package com.homeassignment.cocktails.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.homeassignment.cocktails.R
import com.homeassignment.cocktails.databinding.FragmentCocktailsBinding
import com.homeassignment.cocktails.extentions.asFlow
import com.homeassignment.cocktails.utils.DataUpdate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

class CocktailsFragment : Fragment() {

    private val viewModel: CocktailsViewModel by activityViewModels()
    private lateinit var binding: FragmentCocktailsBinding
    private var loaderView: ProgressBar? = null
    private var emptyView: TextView? = null
    private var listView: RecyclerView? = null
    private var adapter: CocktailsAdapter? = null
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCocktailsBinding.inflate(inflater, container, false)
        val view = binding.root
        initViews()
        showEmptyState()
        initViewModel()
        setupSearchView()
        return view
    }

    private fun initViews() {
        binding.run {
            loaderView = fragmentCocktailsProgress
            listView = fragmentCocktailsList
            emptyView = fragmentCocktailsEmptyView
            searchView = fragmentCocktailsSearchView
        }
        adapter = CocktailsAdapter()
        listView?.adapter = adapter
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupSearchView() {
        lifecycleScope.launchWhenResumed {
            searchView?.asFlow()?.debounce(700)?.collect {
                viewModel.loadDrinks(it)
            }
        }
    }

    private fun initViewModel() {
        viewModel.drinksResult.observe(requireActivity()) {
            when (it) {
                is DataUpdate.Error -> showError(it.message)
                is DataUpdate.Loading -> showLoadingState()
                is DataUpdate.Success -> {
                    showResultsList()
                    if (it.data.isEmpty()) showEmptyState()
                    else adapter?.submitList(it.data)
                }
            }
        }
    }

    private fun showEmptyState() {
        listView?.visibility = View.GONE
        loaderView?.visibility = View.GONE
        emptyView?.let {
            it.visibility = View.VISIBLE
            it.text = getString(R.string.empty_view_text)
        }
    }
    private fun showLoadingState() {
        listView?.visibility = View.GONE
        loaderView?.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
    }
    private fun showResultsList() {
        listView?.visibility = View.VISIBLE
        loaderView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
    }
    private fun showError(message: String?) {
        showEmptyState()
        emptyView?.text = "Oops, something went wrong.\n$message"
    }

    companion object {
        fun newInstance() = CocktailsFragment()
    }
}