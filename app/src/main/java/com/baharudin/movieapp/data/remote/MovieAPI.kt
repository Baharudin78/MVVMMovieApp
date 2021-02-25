package com.baharudin.movieapp.data.remote

import com.baharudin.movieapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    companion object{
        const val BASE_URL ="https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.MOVIE_API_KEY
    }
    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlaying(
        @Query("page")position : Int
    ): MovieResponse
    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page")  page : Int
    ): MovieResponse
}