package com.mario.moviedb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mario.moviedb.core.base.ApiState
import com.mario.moviedb.core.base.BaseViewModel
import com.mario.moviedb.core.datasource.MovieRepository
import com.mario.moviedb.core.model.DetailMovieResponse
import com.mario.moviedb.core.model.ReviewMovieResponse
import com.mario.moviedb.core.model.VideoTrailerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val _detailMovieResult = MutableLiveData<ApiState<DetailMovieResponse>>()
    val detailMovieResult: LiveData<ApiState<DetailMovieResponse>> = _detailMovieResult
    private val _reviewMovieResult = MutableLiveData<ApiState<ReviewMovieResponse>>()
    val reviewMovieResult: LiveData<ApiState<ReviewMovieResponse>> = _reviewMovieResult
    private val _trailerMovieResult = MutableLiveData<ApiState<VideoTrailerResponse>>()
    val trailerMovieResult: LiveData<ApiState<VideoTrailerResponse>> = _trailerMovieResult

    var listMovie: ArrayList<ReviewMovieResponse.Result> = arrayListOf()
    var page = 1
    var totalPage = 1

    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _detailMovieResult.postValue(ApiState.loading())
            try {
                val response = repository.getDetailMovie(movieId)
                _detailMovieResult.postValue(response)
            } catch (e: java.lang.Exception) {
                onError.postValue(e.message)
            }
        }
    }

    fun getReviewMovie(movieId: Int, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _reviewMovieResult.postValue(ApiState.loading())
            try {
                val response = repository.getReviewMovie(movieId, page)
                _reviewMovieResult.postValue(response)
            } catch (e: java.lang.Exception) {
                onError.postValue(e.message)
            }
        }
    }

    fun getTrailerMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _trailerMovieResult.postValue(ApiState.loading())
            try {
                val response = repository.getTrailerMovie(movieId)
                _trailerMovieResult.postValue(response)
            } catch (e: java.lang.Exception) {
                onError.postValue(e.message)
            }
        }
    }
}