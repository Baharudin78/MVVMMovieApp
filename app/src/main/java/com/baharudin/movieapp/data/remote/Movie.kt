package com.baharudin.movieapp.data.remote

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class Movie(
    val id : String,
    val overview : String?,
    val poster_path : String,
    val original_title : String
): Parcelable {
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"
}