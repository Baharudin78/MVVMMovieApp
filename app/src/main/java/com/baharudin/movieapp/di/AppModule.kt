package com.baharudin.movieapp.di

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import com.baharudin.movieapp.data.local.FavoriteMovieDatabase
import com.baharudin.movieapp.data.remote.MovieAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)

object AppModule {
   @Provides
   @Singleton
   fun ProvideFavMovie() : Retrofit =
       Retrofit.Builder()
           .baseUrl(MovieAPI.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

    @Provides
    @Singleton
    fun ProvideMovieApi(retrofit: Retrofit) : MovieAPI =
        retrofit.create(MovieAPI::class.java)

    @Provides
    @Singleton
    fun ProvideFavMovieDatabase(
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,FavoriteMovieDatabase::class.java,
        "movie_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavMovieDao(db: FavoriteMovieDatabase) = db.getFavoriteMovieDao()
}