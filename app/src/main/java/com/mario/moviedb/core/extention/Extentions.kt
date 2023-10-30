package com.mario.moviedb.core.extention

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun ImageView.loadDrawable(drawable: Drawable?, requestOptions: RequestOptions? = null) {
    requestOptions?.let { reqOptions ->
        Glide.with(this).load(drawable).apply(reqOptions).into(this)
    } ?: kotlin.runCatching {
        Glide.with(this).load(drawable).into(this)
    }
}

fun ImageView.loadImage(path: String) {
    Glide.with(this).load(path).into(this)
}

fun ImageView.loadImageWithOption(path: String, requestOptions: RequestOptions) {
    Glide.with(this).load(path).apply(requestOptions).into(this)
}