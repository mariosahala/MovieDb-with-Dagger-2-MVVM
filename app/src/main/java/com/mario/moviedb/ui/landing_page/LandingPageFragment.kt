package com.mario.moviedb.ui.landing_page

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mario.moviedb.R
import com.mario.moviedb.core.base.BaseFragment
import com.mario.moviedb.databinding.FragmentLandingPageBinding


class LandingPageFragment : BaseFragment<FragmentLandingPageBinding>() {

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLandingPageBinding =
        FragmentLandingPageBinding.inflate(layoutInflater, null, false)


    override fun setUpVariable() {
        val handler = Handler()
        handler.postDelayed({
            findNavController().navigate(R.id.action_landingPageFragment_to_genreFragment)
        }, 3000L)
    }

}