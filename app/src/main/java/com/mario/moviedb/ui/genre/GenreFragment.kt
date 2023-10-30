package com.mario.moviedb.ui.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.mario.moviedb.core.base.BaseFragment
import com.mario.moviedb.core.base.State
import com.mario.moviedb.core.di.MovieDaggerWrapper
import com.mario.moviedb.core.extention.gone
import com.mario.moviedb.core.extention.visible
import com.mario.moviedb.core.model.GenreResponse
import com.mario.moviedb.databinding.FragmentGenreBinding
import com.mario.moviedb.ui.list_movie.ListMovieViewModel
import javax.inject.Inject

class GenreFragment : BaseFragment<FragmentGenreBinding>() {
    private lateinit var genreMovieAdapter: GenreMovieAdapter

    private var refreshListener: OnRefreshListener? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<GenreViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MovieDaggerWrapper.getComponent().inject(this@GenreFragment)
    }

    override fun setViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentGenreBinding =
        FragmentGenreBinding.inflate(layoutInflater, null, false)

    override fun onDestroyView() {
        refreshListener = null
        super.onDestroyView()
    }

    override fun setUpVariable() {
        context?.let { ctx ->
            binding.apply {
                refreshListener = OnRefreshListener {
                    viewModel.getListGenre()
                }
                swRoot.setOnRefreshListener(refreshListener)
                viewModel.getListGenre()
                setupObserve(ctx)

            }
        }
    }

    private fun setupObserve(ctx: Context) {
        binding.apply {
            viewModel.listGenreResult.observe(viewLifecycleOwner) {
                when (it.status) {
                    State.LOADING -> {
                        swRoot.gone()
                        flProgress.visible()
                    }

                    State.SUCCESS -> {
                        swRoot.visible()
                        flProgress.gone()
                        it.data?.let { data ->
                            swRoot.isRefreshing = false
                            setupAdapter(ctx, data.genres)
                        }
                    }

                    State.ERROR -> {
                        swRoot.visible()
                        flProgress.gone()
                        swRoot.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun setupAdapter(context: Context, data: ArrayList<GenreResponse.Genre>) {
        binding.apply {
            genreMovieAdapter = GenreMovieAdapter()
            rvGenreMovie.layoutManager = LinearLayoutManager(context)
            rvGenreMovie.adapter = genreMovieAdapter
            genreMovieAdapter.differ.submitList(data)
            genreMovieAdapter.onClick = {
                findNavController().navigate(
                    GenreFragmentDirections.actionGenreFragmentToListMovieFragment(it.id)
                )
            }
        }
    }
}