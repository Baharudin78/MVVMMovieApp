package com.baharudin.movieapp.ui.movie

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.baharudin.movieapp.data.remote.MovieRepository

class MovieViewModel @ViewModelInject constructor(
     val movieRepository: MovieRepository,
    @Assisted state : SavedStateHandle
    ) : ViewModel() {

    companion object{
         const val CURRENT_STATE = "current_query,"
         const val EMPTY_QUERY = ""
    }
    private val currenryQuery = state.getLiveData(CURRENT_STATE, EMPTY_QUERY)
    val movies = currenryQuery.switchMap { query ->
        if (!query.isEmpty()){
            movieRepository.searchMovie(query)
        }else{
            movieRepository.getMovieNowPlaying().cachedIn(viewModelScope)
        }
    }
    fun searchMovie(query : String){
        currenryQuery.value = query
    }
}