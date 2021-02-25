package com.baharudin.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.movieapp.R
import com.baharudin.movieapp.data.remote.Movie
import com.baharudin.movieapp.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MovieAdapter(private val listener : OnClickItemListener) : PagingDataAdapter<Movie, MovieAdapter.MovieHolder> (COMPARATOR) {
    inner class MovieHolder(private var binding : ItemMovieBinding) :
            RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item!= null){
                        listener.OnItemClick(item)
                    }
                }
            }
        }
                fun bindData(movie: Movie){
                    with(binding){
                        Glide.with(itemView)
                            .load("${movie.baseUrl}${movie.poster_path}")
                            .centerCrop()
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .error(R.drawable.ic_error)
                            .into(ivImage)

                        tvJudul.text = movie.original_title
                    }
                }
            }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null){
            holder.bindData(currentItem)
        }
    }

    companion object{
        val COMPARATOR = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }
    interface OnClickItemListener{
        fun OnItemClick(movie: Movie)
    }


}