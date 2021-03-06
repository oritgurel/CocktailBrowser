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
import com.homeassignment.cocktails.data.model.Drink
import com.homeassignment.cocktails.databinding.FragmentCocktailsBinding
import com.homeassignment.cocktails.extentions.asFlow
import com.homeassignment.cocktails.utils.DataUpdate
import com.homeassignment.cocktails.viewmodels.CocktailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce

private const val DEBOUNCE_INTERVAL = 700L

@FlowPreview
@ExperimentalCoroutinesApi
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

    private fun setupSearchView() {
        lifecycleScope.launchWhenResumed {
            searchView?.asFlow()?.debounce(DEBOUNCE_INTERVAL)?.collect { query ->
                if (query.isNullOrBlank()) {
                    showEmptyState()
                } else {
                    viewModel.loadDrinks(query) }
                }
        }
    }

    private fun initViewModel() {
        viewModel.drinksResult.observe(requireActivity()) {
            when (it) {
                is DataUpdate.Error -> showError(it.message)
                is DataUpdate.Loading -> showLoadingState()
                is DataUpdate.Success -> onDataUpdateSuccess(it.data)
            }
        }
    }

    private fun onDataUpdateSuccess(data: List<Drink>) {
        if (data.isEmpty()) {
            showEmptyState()
        } else {
            showResultsList()
            adapter?.submitList(data)
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
        emptyView?.text = getString(R.string.error_message, message)
    }

    companion object {
        fun newInstance() = CocktailsFragment()
    }
}