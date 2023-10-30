package com.mario.moviedb.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mario.moviedb.core.base.BaseFragment
import com.mario.moviedb.core.base.State
import com.mario.moviedb.core.constant.Constants
import com.mario.moviedb.core.di.MovieDaggerWrapper
import com.mario.moviedb.core.extention.gone
import com.mario.moviedb.core.extention.loadImage
import com.mario.moviedb.core.extention.visible
import com.mario.moviedb.core.model.DetailMovieResponse
import com.mario.moviedb.core.model.VideoTrailerResponse
import com.mario.moviedb.databinding.FragmentDetailMovieBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import javax.inject.Inject


class DetailMovieFragment : BaseFragment<FragmentDetailMovieBinding>() {
    private lateinit var reviewMovieAdapter: ReviewMovieAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<DetailMovieViewModel> { viewModelFactory }
    private val argsMovie: DetailMovieFragmentArgs by navArgs()
    private var scrollListener: OnScrollChangeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MovieDaggerWrapper.getComponent().inject(this@DetailMovieFragment)
    }

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailMovieBinding = FragmentDetailMovieBinding.inflate(layoutInflater, null, false)

    override fun setUpVariable() {
        context?.let {
            scrollListener = OnScrollChangeListener { v, _, scrollY, _, _ ->
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight && viewModel.page < viewModel.totalPage) {
                    viewModel.page++
                    viewModel.getReviewMovie(argsMovie.movieId, viewModel.page)
                }
            }
            binding.nsvRootDetailMovie.setOnScrollChangeListener(scrollListener)
            viewModel.getDetailMovie(argsMovie.movieId)
            viewModel.getReviewMovie(argsMovie.movieId, viewModel.page)
            viewModel.getTrailerMovie(argsMovie.movieId)
            setupObserve(it)
        }
    }

    private fun setupObserve(context: Context) {
        binding.apply {
            viewModel.detailMovieResult.observe(viewLifecycleOwner) {
                when (it.status) {
                    State.LOADING -> {
                        flProgressRoot.visible()
                        clRoot.gone()
                    }

                    State.SUCCESS -> {
                        it.data?.let { data ->
                            setupView(data)
                        }
                        flProgressRoot.gone()
                        clRoot.visible()
                    }

                    State.ERROR -> {
                        flProgressRoot.gone()
                        clRoot.visible()
                    }
                }
            }

            viewModel.reviewMovieResult.observe(viewLifecycleOwner) {
                when (it.status) {
                    State.LOADING -> {
                        if (viewModel.page == 1) {
                            flProgressReview.visible()
                            rvReviewMovie.gone()
                        }
                    }

                    State.SUCCESS -> {
                        it.data?.let { data ->
                            viewModel.totalPage = data.totalPages
                            viewModel.listMovie.addAll(data.results)
                            setupAdapter(context)
                        }
                        flProgressReview.gone()
                        rvReviewMovie.visible()
                    }

                    State.ERROR -> {
                        flProgressReview.gone()
                        rvReviewMovie.visible()
                    }
                }
            }

            viewModel.trailerMovieResult.observe(viewLifecycleOwner) {
                when (it.status) {
                    State.LOADING -> {}
                    State.SUCCESS -> {
                        it.data?.let { data ->
                            trailerMovie(data.results)
                        }
                    }

                    State.ERROR -> {}
                }
            }
        }
    }

    private fun setupView(data: DetailMovieResponse) {
        binding.apply {
            ivPoster.loadImage(Constants.IMAGE_BASE + data.posterPath)
            tvDetailMovieTitle.text = data.title
            tvDetailMovieYear.text = data.releaseDate
            tvDetailDescription.text = data.overview
            tvMovieDetailRate.text = data.voteAverage.toString()
            tvMovieDetailLike.text = data.voteCount.toString()
        }
    }

    private fun setupAdapter(context: Context) {
        binding.apply {
            reviewMovieAdapter = ReviewMovieAdapter()
            rvReviewMovie.layoutManager = LinearLayoutManager(context)
            rvReviewMovie.adapter = reviewMovieAdapter
            reviewMovieAdapter.differ.submitList(viewModel.listMovie)
        }
    }

    private fun trailerMovie(data: ArrayList<VideoTrailerResponse.Result>) {
        binding.apply {
            val youTubePlayerView: YouTubePlayerView = youtubePlayerView
            youTubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    if (data.isEmpty()) {
                        youtubePlayerView.isEnabled = false
                        youtubePlayerView.isClickable = false
                        youtubePlayerView.gone()
                    } else {
                        youTubePlayer.cueVideo(data[0].key, 0f)
                    }
                }
            })
        }
    }
}