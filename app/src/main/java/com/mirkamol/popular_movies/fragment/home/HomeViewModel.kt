package com.mirkamol.popular_movies.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkamol.popular_movies.model.TVShow
import com.mirkamol.popular_movies.model.TVShowPopular
import com.mirkamol.popular_movies.model.TVShowDetails
import com.mirkamol.popular_movies.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowsFromDB = MutableLiveData<List<TVShow>>()

    val tvShowPopular = MutableLiveData<TVShowPopular>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    /**
     * Retrofit Related
     */




    fun apiTVShowPopular(page:Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowPopular(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    tvShowPopular.postValue(resp!!)
//                    var localShows = tvShowsFromApi.value
//                    if (localShows == null) localShows = ArrayList()
//                    val serverShows = resp.tv_shows
//                    localShows.addAll(serverShows)

                    tvShowsFromApi.postValue(resp.tv_shows)
                    isLoading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun apiTVShowDetails(show_id:Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowDetails(show_id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val reps = response.body()
                    tvShowDetails.postValue(reps!!)
                    isLoading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    /**
     * Room Related
     */

    fun getTVShowsFromDB(){
        viewModelScope.launch {
            val tvShows = tvShowRepository.getTVShowsFromDB()
            tvShowsFromDB.postValue(tvShows)
        }

    }

    fun insertTVShowToDB(tvShow: TVShow){
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }

    }

    fun deleteTvshowsFromDB(){
        viewModelScope.launch {
            tvShowRepository.deleteTvshowsFromDB()
        }

    }


}