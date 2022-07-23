package com.mirkamol.popular_movies.fragment.save

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mirkamol.popular_movies.model.TVShow
import com.mirkamol.popular_movies.R
import com.mirkamol.popular_movies.adapter.TVShowAdapter
import com.mirkamol.popular_movies.adapter.TVShowSaveAdapter
import com.mirkamol.popular_movies.databinding.FragmentHomeBinding
import com.mirkamol.popular_movies.databinding.FragmentSaveBinding
import com.mirkamol.popular_movies.fragment.home.HomeViewModel
import com.mirkamol.popular_movies.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFragment : Fragment(R.layout.fragment_save) {
    val viewModel by viewModels<SaveViewModel>()
    lateinit var adapter: TVShowSaveAdapter
    private val binding by viewBinding(FragmentSaveBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        initObserves()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.layoutManager = gridLayoutManager
        refreshAdapter(ArrayList())

        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }
        viewModel.getTVShowsFromDB()


    }

    private fun initObserves() {
// Retrofit Related
        viewModel.tvShowsFromDB.observe(viewLifecycleOwner) {
            Logger.d("@@", it.size.toString())
            adapter.setViewTVShows(it)


        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(ContentValues.TAG, it.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Logger.d(ContentValues.TAG, it.toString())
            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowSaveAdapter(this, items)
        binding.rvHome.adapter = adapter
    }

}