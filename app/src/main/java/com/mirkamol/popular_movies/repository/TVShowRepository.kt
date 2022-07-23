package com.mirkamol.popular_movies.repository

import com.mirkamol.popular_movies.model.TVShow
import com.mirkamol.popular_movies.db.TVShowDao
import com.mirkamol.popular_movies.network.TVshowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tVshowService: TVshowService, private val tvShowDao: TVShowDao) {

    /*
   * Retrofit Related
     */

    suspend fun apiTVShowPopular(page: Int) = tVshowService.apiTVshowPopular(page)
    suspend fun apiTVShowDetails(q: Int) = tVshowService.apiTVShowDetails(q)


    /**
     * Room Related
     */

    suspend fun getTVShowsFromDB() = tvShowDao.getTVShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTvShowDB(tvShow)
    suspend fun deleteTvshowsFromDB() = tvShowDao.deleteTVShowsFromDB()
}
