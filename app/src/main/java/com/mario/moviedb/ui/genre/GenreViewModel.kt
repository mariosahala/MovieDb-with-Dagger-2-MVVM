package com.mario.moviedb.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mario.moviedb.core.base.ApiState
import com.mario.moviedb.core.base.BaseResponse
import com.mario.moviedb.core.base.BaseViewModel
import com.mario.moviedb.core.datasource.MovieRepository
import com.mario.moviedb.core.model.GenreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenreViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {
    private val _listGenreResult =
        MutableLiveData<ApiState<GenreResponse>>()
    val listGenreResult: LiveData<ApiState<GenreResponse>> =
        _listGenreResult

    fun getListGenre() {
        viewModelScope.launch(Dispatchers.IO) {
            _listGenreResult.postValue(ApiState.loading())
            try {
                val response = repository.getGenreList()
                _listGenreResult.postValue(response)
            } catch (e: java.lang.Exception) {
                onError.postValue(e.message)
            }
        }
    }
}