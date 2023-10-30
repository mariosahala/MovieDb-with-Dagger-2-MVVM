package com.mario.moviedb

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mario.moviedb.core.base.BaseActivity
import com.mario.moviedb.core.di.MovieDaggerWrapper
import com.mario.moviedb.core.extention.gone
import com.mario.moviedb.core.extention.visible
import com.mario.moviedb.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        MovieDaggerWrapper.buildComponent(this.applicationContext)
        super.onCreate(savedInstanceState)
    }
    override fun setViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setUpVariable() {
        binding.apply {
            navController = Navigation.findNavController(this@MainActivity, R.id.container_movie)
            navController.addOnDestinationChangedListener { _, navFragment, _ ->
                mainToolbar.tvToolbarTitle.text = navFragment.label
                when (navFragment.id) {
                    R.id.genreFragment -> {
                        mainToolbar.root.visible()
                        mainToolbar.ivBack.setOnClickListener {
                            finish()
                        }
                    }

                    R.id.landingPageFragment -> {
                        mainToolbar.root.gone()
                    }

                    else -> {
                        mainToolbar.root.visible()
                        mainToolbar.ivBack.setOnClickListener {
                            navController.navigateUp()
                        }
                    }
                }
            }
        }
    }
}