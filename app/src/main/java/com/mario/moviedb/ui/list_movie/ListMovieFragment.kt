package com.mario.moviedb.ui.list_movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mario.moviedb.core.base.BaseFragment
import com.mario.moviedb.core.base.State
import com.mario.moviedb.core.di.MovieDaggerWrapper
import com.mario.moviedb.core.extention.gone
import com.mario.moviedb.core.extention.visible
import com.mario.moviedb.databinding.FragmentListMovieBinding
import javax.inject.Inject

class ListMovieFragment : BaseFragment<FragmentListMovieBinding>() {
    private lateinit var listMovieAdapter: ListMovieAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ListMovieViewModel> { viewModelFactory }
    private val argsMovie: ListMovieFragmentArgs by navArgs()
    private var scrollListener: OnScrollChangeListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        MovieDaggerWrapper.getComponent().inject(this@ListMovieFragment)
    }

    override fun onDestroy() {
        scrollListener = null
        super.onDestroy()
    }

    override fun setViewBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentListMovieBinding = FragmentListMovieBinding.inflate(layoutInflater, null, false)

    override fun setUpVariable() {
        context?.let {
            scrollListener = OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    viewModel.page++
                    viewModel.getListMovie(argsMovie.genreArg, viewModel.page)
                }
            }
            binding.apply {
                nsvRoot.setOnScrollChangeListener(scrollListener)
                viewModel.getListMovie(argsMovie.genreArg, viewModel.page)
                setupObserve(it)
            }
        }
    }

    private fun setupObserve(context: Context) {
        binding.apply {
            viewModel.listMovieResult.observe(viewLifecycleOwner) {
                when (it.status) {
                    State.LOADING -> {
                        if (viewModel.page == 1) {
                            rvListMovie.gone()
                            flProgress.visible()
                        }
                    }

                    State.SUCCESS -> {
                        rvListMovie.visible()
                        flProgress.gone()
                        it.data?.let { data ->
                            viewModel.listMovie.addAll(data.results)
                            setupAdapter(context)
                        }
                    }

                    State.ERROR -> {
                        rvListMovie.visible()
                        flProgress.gone()
                    }
                }
            }
        }
    }

    private fun setupAdapter(context: Context) {
        binding.apply {
            listMovieAdapter = ListMovieAdapter()
            rvListMovie.layoutManager = GridLayoutManager(context, 2)
            rvListMovie.adapter = listMovieAdapter
            listMovieAdapter.differ.submitList(viewModel.listMovie)
            listMovieAdapter.onClick = {
                findNavController().navigate(
                    ListMovieFragmentDirections.actionListMovieFragmentToDetailMovieFragment(it.id)
                )
            }
        }
    }
}