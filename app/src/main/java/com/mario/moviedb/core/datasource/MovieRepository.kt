package com.mario.moviedb.core.datasource

import com.mario.moviedb.core.base.ApiState
import com.mario.moviedb.core.model.DetailMovieResponse
import com.mario.moviedb.core.model.GenreResponse
import com.mario.moviedb.core.model.ListMovieResponse
import com.mario.moviedb.core.model.ReviewMovieResponse
import com.mario.moviedb.core.model.VideoTrailerResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getGenreList(
    ): ApiState<GenreResponse> {
        apiService.getGenreList().let {
            return ApiState.returnData(it)
        }
    }

    suspend fun getListMovie(movieId: Int, page: Int): ApiState<ListMovieResponse> {
        apiService.getMovieList(movieId, page).let {
            return ApiState.returnData(it)
        }
    }

    suspend fun getDetailMovie(movieId: Int): ApiState<DetailMovieResponse> {
        apiService.getDetailMovie(movieId).let {
            return ApiState.returnData(it)
        }
    }

    suspend fun getReviewMovie(movieId: Int,page: Int): ApiState<ReviewMovieResponse> {
        apiService.getReviewMovie(movieId,page).let {
            return ApiState.returnData(it)
        }
    }

    suspend fun getTrailerMovie(movieId: Int): ApiState<VideoTrailerResponse> {
        apiService.getVideoTrailer(movieId).let {
            return ApiState.returnData(it)
        }
    }
}