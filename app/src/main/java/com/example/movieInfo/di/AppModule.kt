package com.example.movieInfo.di

import com.example.movieInfo.data.repository.MovieRepository
import com.example.movieInfo.data.sources.remote.ApiService
import com.example.movieInfo.data.sources.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: RemoteDataSource): MovieRepository {
        return MovieRepository(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }
}