package com.mirkamol.popular_movies

import com.mirkamol.popular_movies.di.AppModule
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkStatusCode() = runTest {
        val response = AppModule().tvShowService().apiTVshowPopular(1)
        assertEquals(response.code(),200)
    }

    @Test
    fun responseIsSuccessful() = runTest {
        val response = AppModule().tvShowService().apiTVshowPopular(1)
        assertTrue(response.isSuccessful)
    }

    @Test
    fun checkTVShowListNotNull() = runTest {
        val response = AppModule().tvShowService().apiTVshowPopular(1)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)
    }

    @Test
    fun checkTVShowListSize() = runTest {
        val response = AppModule().tvShowService().apiTVshowPopular(1)
        assertEquals(response.body()!!.tv_shows.size, 20)
    }

    @Test
    fun checkFirstTVShowStatus() = runTest {
        val response = AppModule().tvShowService().apiTVshowPopular(1)
        val tvShowPopular = response.body()
        val tvShows = tvShowPopular!!.tv_shows
        val tvShow = tvShows[0]
        assertEquals(tvShow.status, "Canceled/Ended")

    }

    @Test
    fun checkStatusCodeDetails() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(1)
        assertEquals(200, response.code())
    }

    @Test
    fun responseIsSuccessfulDetails() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(1)
        assertTrue(response.isSuccessful)
    }
    @Test
    fun checkTVShowListNotNullDetails() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(1)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tvShow)
    }

    @Test
    fun checkTVShowListSizeDetails() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(1)
        assertEquals(response.body()!!.tvShow, 20)
    }

    @Test
    fun checkFirstTVShowStatusDetails() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(1)
        val tvShowPopular = response.body()
        val tvShows = tvShowPopular!!.tvShow
        assertEquals(tvShows.status, "Canceled")

    }

}