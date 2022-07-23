package com.mirkamol.popular_movies.network
import com.mirkamol.popular_movies.model.TVShowPopular
import com.mirkamol.popular_movies.model.TVShowDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TVshowService {

    @GET("api/most-popular")
    suspend fun apiTVshowPopular(@Query("page")page:Int):Response<TVShowPopular>

    @GET("api/show-details")
    suspend fun apiTVShowDetails(@Query("q")q:Int):Response<TVShowDetails>
}