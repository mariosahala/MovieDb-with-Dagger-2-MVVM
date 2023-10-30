package com.mario.moviedb.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    var onError: MutableLiveData<String> = MutableLiveData()
    var state: MutableLiveData<State> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}