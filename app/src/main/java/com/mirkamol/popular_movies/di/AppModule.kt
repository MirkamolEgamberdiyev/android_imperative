package com.mirkamol.popular_movies.di

import android.app.Application
import com.mirkamol.popular_movies.db.AppDatabase
import com.mirkamol.popular_movies.db.TVShowDao
import com.mirkamol.popular_movies.network.Server
import com.mirkamol.popular_movies.network.Server.IS_TESTER
import com.mirkamol.popular_movies.network.TVshowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Retrofit Related
     */
    @Provides
    fun server(): String {
        if (IS_TESTER) return Server.getDevelopment()
        return Server.getProduction()
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService(): TVshowService {
        return retrofitClient().create(TVshowService::class.java)
    }

    /**
     * Room Related
     */
    @Provides
    @Singleton
    fun appDatabse(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase): TVShowDao {
        return appDatabase.getTVShowDao()
    }

}