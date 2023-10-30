package com.mario.moviedb.core.di

import android.content.Context

object MovieDaggerWrapper {
    private var component: ApplicationComponent? = null

    fun getComponent(): ApplicationComponent {
        assert(component != null)
        return component!!
    }

    fun buildComponent(context: Context) {
        component = DaggerApplicationComponent.builder().bindContext(context).build()
    }

    fun clearComponent() {
        component = null
    }

}