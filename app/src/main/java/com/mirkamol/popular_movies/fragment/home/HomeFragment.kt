package com.mirkamol.popular_movies.fragment.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mirkamol.popular_movies.model.TVShow
import com.mirkamol.popular_movies.R
import com.mirkamol.popular_movies.adapter.TVShowAdapter
import com.mirkamol.popular_movies.databinding.FragmentHomeBinding
import com.mirkamol.popular_movies.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    val viewModel by viewModels<HomeViewModel>()
    lateinit var adapter: TVShowAdapter
    private val binding by viewBinding(FragmentHomeBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initObserves()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.layoutManager = gridLayoutManager
        refreshAdapter(ArrayList())
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    viewModel.apiTVShowPopular(nextPage)
                }
            }
        })
        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }
        viewModel.apiTVShowPopular(1)
    }

    private fun initObserves() {
// Retrofit Related
        viewModel.tvShowsFromApi.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.size.toString())
            adapter.setViewTVShows(it)

            Log.d(TAG, "initObserves: ${it.size}")

        }


        viewModel.tvShowDetails.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            Logger.d(TAG, it.toString())
            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        })
    }

    private fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(this, items)
        binding.rvHome.adapter = adapter
    }

    fun callDetailsFragment(tvShow: TVShow, sharedImageView: ImageView) {
        findNavController().navigate(
            R.id.action_homeFragment_to_detailsFragment, bundleOf(
                "show_id" to tvShow.id,
                "show_img" to tvShow.image_thumbnail_path,
                "show_name" to tvShow.name,
                "show_network" to tvShow.network,
                "iv_movie" to ViewCompat.getTransitionName(sharedImageView)
            ), null,
            FragmentNavigatorExtras(sharedImageView to "something")
        )

    }

}