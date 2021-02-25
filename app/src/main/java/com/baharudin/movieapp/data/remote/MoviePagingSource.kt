package com.baharudin.movieapp.data.remote

import androidx.paging.PagingSource
import retrofit2.HttpException
import java.io.IOException

private var STARTING_PAGE_INDEX = 1
class MoviePagingSource(
    private val movieAPI: MovieAPI,
    private val query : String?
) : PagingSource<Int,Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = if (query != null) movieAPI.searchMovie(query,position) else movieAPI.getNowPlaying(position)
            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position-1,
                nextKey = if (movies.isEmpty()) null else position+1
            )
        }catch (e : IOException){
            LoadResult.Error(e)
        }catch (e : HttpException){
            LoadResult.Error(e)
        }
    }
}