package com.corcida.dmovie.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corcida.dmovie.databinding.FragmentMoviesBinding
import com.corcida.dmovie.ui.common.adapter.MoviesAdapter
import com.corcida.dmovie.ui.movies.MoviesUiModel.Content
import com.corcida.dmovie.ui.movies.MoviesUiModel.Error
import com.corcida.dmovie.ui.movies.MoviesUiModel.Loading
import com.corcida.domain.MovieType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val topRatedAdapter = MoviesAdapter()
    private val popularAdapter = MoviesAdapter()
    private val upcomingAdapter = MoviesAdapter()
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        setViews()
        viewModel.model.observe(viewLifecycleOwner, Observer(this::observeUiModel))
        viewModel.getData(getTypesWithNoData())
        return binding.root
    }
    private fun setViews(){
        setAdapters()
        binding.refreshLayout.isEnabled = false
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshData(getTypesWithNoData())
        }
    }

    private fun setAdapters(){
        binding.topRated.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.popular.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        binding.upcoming.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        binding.topRated.adapter = topRatedAdapter
        binding.upcoming.adapter = upcomingAdapter
        binding.popular.adapter = popularAdapter
    }

    private fun observeUiModel(model: MoviesUiModel){
        when (model){
            is Content -> {
                binding.refreshLayout.isRefreshing = false
                getAdapter(model.type).movies = model.movies
            }
            is Error -> {
                binding.refreshLayout.isEnabled = true
                binding.refreshLayout.isRefreshing = false
                getErrorLayout(model.type).visibility = View.VISIBLE
                getRecyclerView(model.type).visibility = View.GONE
            }
            is Loading -> {
                binding.refreshLayout.isEnabled = false
                getErrorLayout(model.type).visibility = View.GONE
                getRecyclerView(model.type).visibility = View.VISIBLE
            }
        }
    }

    private fun getAdapter(type : MovieType) : MoviesAdapter {
        return when (type) {
            MovieType.POPULAR -> popularAdapter
            MovieType.TOP_RATED -> topRatedAdapter
            MovieType.UPCOMING -> upcomingAdapter
        }
    }

    private fun getRecyclerView(type : MovieType) : RecyclerView {
        return when (type) {
            MovieType.POPULAR -> binding.popular
            MovieType.TOP_RATED -> binding.topRated
            MovieType.UPCOMING -> binding.upcoming
        }
    }

    private fun getErrorLayout(type : MovieType) : LinearLayout {
        return when (type) {
            MovieType.POPULAR -> binding.popularError.root
            MovieType.TOP_RATED -> binding.topRatedError.root
            MovieType.UPCOMING -> binding.upcomingError.root
        }
    }

    private fun getTypesWithNoData() : List<MovieType>{
        val list = mutableListOf<MovieType>()
        MovieType.entries.onEach {
            if (getAdapter(it).movies.all { it == null })
                list.add(it)
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}