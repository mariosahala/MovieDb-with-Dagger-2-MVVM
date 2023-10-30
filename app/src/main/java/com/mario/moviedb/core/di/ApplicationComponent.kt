package com.mario.moviedb.core.di

import android.content.Context
import com.mario.moviedb.core.di.annotation.FeatureScope
import com.mario.moviedb.ui.detail.DetailMovieFragment
import com.mario.moviedb.ui.genre.GenreFragment
import com.mario.moviedb.ui.list_movie.ListMovieFragment
import dagger.BindsInstance
import dagger.Component


@FeatureScope
@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(genreFragment: GenreFragment)
    fun inject(listMovieFragment: ListMovieFragment)
    fun inject(detailMovieFragment: DetailMovieFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): ApplicationComponent

    }
}