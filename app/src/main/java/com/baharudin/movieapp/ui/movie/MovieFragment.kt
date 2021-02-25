package com.baharudin.movieapp.ui.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.baharudin.movieapp.R
import com.baharudin.movieapp.adapter.MovieAdapter
import com.baharudin.movieapp.adapter.MovieFooterAdapter
import com.baharudin.movieapp.data.remote.Movie
import com.baharudin.movieapp.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) ,MovieAdapter.OnClickItemListener{
    private val viewModel by viewModels<MovieViewModel> ()
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieBinding.bind(view)

        val adapter = MovieAdapter(this)
        binding.apply {
            rvMovie.setHasFixedSize(true)
            rvMovie.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MovieFooterAdapter{adapter.retry()},
                footer = MovieFooterAdapter{adapter.retry()}
            )
            button.setOnClickListener {
                adapter.retry()
            }
        }
        adapter.addLoadStateListener {loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                button.isVisible  = loadState.source.refresh is LoadState.Error
                tvNotfound.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1){
                    rvMovie.isVisible = false
                    tvNotfound.isVisible = true
                }else{
                    tvNotfound.isVisible = false
                }
            }
        }

        viewModel.movies.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.item_search,menu)

        val searchItem = menu.findItem(R.id.searchBar)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener( object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.rvMovie.scrollToPosition(0)
                    viewModel.searchMovie(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun OnItemClick(movie: Movie) {
        val actio = MovieFragmentDirections.actionIdMovieToNavDetails(movie)
        findNavController().navigate(actio)
    }

}