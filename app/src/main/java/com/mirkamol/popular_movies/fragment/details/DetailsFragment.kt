package com.mirkamol.popular_movies.fragment.details

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.mirkamol.popular_movies.R
import com.mirkamol.popular_movies.adapter.TVEpisodeAdapter
import com.mirkamol.popular_movies.adapter.TVshortAdapter
import com.mirkamol.popular_movies.databinding.FragmentDetailsBinding
import com.mirkamol.popular_movies.model.Episode
import com.mirkamol.popular_movies.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val TAG = DetailsFragment::class.java.simpleName
    val viewModel by viewModels<DetailViewModel>()
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    var show_id = ""
    var show_img = ""
    var show_name = ""
    var show_network = ""
    var iv_movie = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition

        arguments?.let {
            show_id = it.get("show_id").toString()
            show_img = it.get("show_img").toString()
            show_name = it.get("show_name").toString()
            show_network = it.get("show_network").toString()
            iv_movie = it.get("iv_movie").toString()
            Log.d(TAG, "onCreate: $show_name")

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        initObserves()
        initEpisodeObserves()
        binding.rvShorts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvEpisode.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val iv_detail = binding.ivDetail
        binding.ivClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(iv_detail)

        viewModel.apiTVShowDetails(show_id.toInt())
        viewModel.apiEpisode(show_id.toInt())
    }

    private fun initObserves() {
        // Retrofit Related

        viewModel.tvShowDetails.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            refreshAdapter(it.tvShow.pictures)
            binding.tvDescription.text = it.tvShow.description
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {

            Logger.d(TAG, it.toString())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())

            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(items: List<String>) {
        val adapter = TVshortAdapter(this, items)
        binding.rvShorts.adapter = adapter
    }

    private fun initEpisodeObserves() {
        viewModel.tvEpisode.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            refreshAdapterEpisode(it.tvShow.episodes)
            Log.d("TAG", "initObserves: ${it.tvShow.episodes}")


        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())

            if (viewModel.isLoading.value == true) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapterEpisode(items: List<Episode>) {
        val adapter = TVEpisodeAdapter(items)
        binding.rvEpisode.adapter = adapter
    }


}