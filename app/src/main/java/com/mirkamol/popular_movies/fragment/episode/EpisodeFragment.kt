package com.mirkamol.popular_movies.fragment.episode

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mirkamol.popular_movies.R
import com.mirkamol.popular_movies.adapter.TVEpisodeAdapter
import com.mirkamol.popular_movies.adapter.TVshortAdapter
import com.mirkamol.popular_movies.databinding.FragmentDetailsBinding
import com.mirkamol.popular_movies.databinding.FragmentEpisodeBinding
import com.mirkamol.popular_movies.fragment.details.DetailViewModel
import com.mirkamol.popular_movies.fragment.details.DetailsFragment
import com.mirkamol.popular_movies.model.Episode
import com.mirkamol.popular_movies.utils.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment(R.layout.fragment_episode) {
    private val binding by viewBinding(FragmentEpisodeBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }



}