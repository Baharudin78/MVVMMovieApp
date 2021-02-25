package com.baharudin.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.movieapp.R
import com.baharudin.movieapp.databinding.ItemFooterBinding

class MovieFooterAdapter(val retry : ()-> Unit) : LoadStateAdapter<MovieFooterAdapter.MovieFooterHolder>() {
    inner class MovieFooterHolder(private var binding: ItemFooterBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.btRetry.setOnClickListener {
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){
            with(binding){
                progressBar.isVisible = loadState is LoadState.Loading
                btRetry.isVisible = loadState !is LoadState.Loading
                tvAlert.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: MovieFooterHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieFooterHolder {
        val inflater = ItemFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieFooterHolder(inflater)
    }

}