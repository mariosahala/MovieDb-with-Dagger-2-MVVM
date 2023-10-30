package com.mario.moviedb.ui.list_movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mario.moviedb.R
import com.mario.moviedb.core.extention.loadImage
import com.mario.moviedb.core.model.ListMovieResponse
import com.mario.moviedb.databinding.ItemListMovieBinding

class ListMovieAdapter : RecyclerView.Adapter<ListMovieAdapter.ListMovieViewHolder>() {
    var onClick: ((ListMovieResponse.Result) -> Unit)? = null


    private val diffCallBack = object : DiffUtil.ItemCallback<ListMovieResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: ListMovieResponse.Result,
            newItem: ListMovieResponse.Result
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ListMovieResponse.Result,
            newItem: ListMovieResponse.Result
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)

    inner class ListMovieViewHolder(private val binding: ItemListMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListMovieResponse.Result) {
            binding.apply {
                ivMoviePhoto.loadImage(IMAGE_BASE + data.posterPath)
                tvMovieTitle.text = data.title
                root.setOnClickListener {
                    onClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMovieViewHolder {
        return ListMovieViewHolder(
            ItemListMovieBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_list_movie, parent, false
                )
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ListMovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    companion object {
        const val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    }
}