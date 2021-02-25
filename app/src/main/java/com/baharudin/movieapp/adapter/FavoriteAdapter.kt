package com.baharudin.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.movieapp.R
import com.baharudin.movieapp.data.local.FavoriteMovie
import com.baharudin.movieapp.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {

    private lateinit var list : List<FavoriteMovie>


    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setMovieList(list: List<FavoriteMovie>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class FavoriteHolder(private val binding : ItemMovieBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteMovie: FavoriteMovie){
            with(binding){
                Glide.with(itemView)
                    .load("${favoriteMovie.baseUrl} & ${favoriteMovie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivImage)
                tvJudul.text = favoriteMovie.original_title
                binding.root.setOnClickListener {onItemClickCallback?.onItemClick(favoriteMovie) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val inflater = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteHolder(inflater)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        Log.e("adapter","masuk view bind adaper")
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    interface OnItemClickCallback{
        fun onItemClick(favoriteMovie : FavoriteMovie)
    }
}