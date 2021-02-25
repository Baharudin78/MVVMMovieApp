package com.baharudin.movieapp.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class FavoriteViewModel @ViewModelInject constructor(
    repository : FavoriteMovieRepository
): ViewModel(){
    val movie = repository.getFavoriteMovies()
}