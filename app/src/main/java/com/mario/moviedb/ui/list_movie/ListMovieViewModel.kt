package com.mario.moviedb.ui.list_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mario.moviedb.core.base.ApiState
import com.mario.moviedb.core.base.BaseViewModel
import com.mario.moviedb.core.datasource.MovieRepository
import com.mario.moviedb.core.model.ListMovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListMovieViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {
    private val _listMovieResult = MutableLiveData<ApiState<ListMovieResponse>>()
    val listMovieResult: LiveData<ApiState<ListMovieResponse>> = _listMovieResult
    var listMovie: ArrayList<ListMovieResponse.Result> = arrayListOf()
    var page = 1

    fun getListMovie(movieId: Int, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _listMovieResult.postValue(ApiState.loading())
            try {
                val response = repository.getListMovie(movieId, page)
                _listMovieResult.postValue(response)
            } catch (e: java.lang.Exception) {
                onError.postValue(e.message)
            }
        }
    }
}
