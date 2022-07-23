package com.mirkamol.popular_movies.fragment.save

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkamol.popular_movies.model.TVShow
import com.mirkamol.popular_movies.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveViewModel@Inject constructor(private val tvShowRepository: TVShowRepository):ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromDB = MutableLiveData<List<TVShow>>()

    fun getTVShowsFromDB(){
        viewModelScope.launch {
            val tvShows = tvShowRepository.getTVShowsFromDB()
            tvShowsFromDB.postValue(tvShows)
        }
    }
}