package com.mario.moviedb.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mario.moviedb.core.di.annotation.ViewModelKey
import com.mario.moviedb.core.view_model_factory.ViewModelFactory
import com.mario.moviedb.ui.detail.DetailMovieViewModel
import com.mario.moviedb.ui.genre.GenreViewModel
import com.mario.moviedb.ui.list_movie.ListMovieViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GenreViewModel::class)
    abstract fun bindGenreViewModel(employeeViewModel: GenreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ListMovieViewModel::class)
    abstract fun bindListMovieViewModel(listMovieViewModel: ListMovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailMovieViewModel::class)
    abstract fun bindDetailMovieViewModel(detailMovieViewModel: DetailMovieViewModel): ViewModel

    @Binds
    abstract fun bindAboutViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}