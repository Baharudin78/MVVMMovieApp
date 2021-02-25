package com.baharudin.movieapp.ui.favorite

import androidx.lifecycle.LiveData
import com.baharudin.movieapp.data.local.FavoriteMovie
import com.baharudin.movieapp.data.local.FavoriteMovieDao
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
) {
    suspend fun addToFavorite(favoriteMovie : FavoriteMovie)= favoriteMovieDao.addToFavorite(favoriteMovie)
    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()
    suspend fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)
    suspend fun removeFavoriteMovie(id : String){
        favoriteMovieDao.removeFromFavorite(id)
    }
}