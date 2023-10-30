package com.mario.moviedb.ui.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mario.moviedb.R
import com.mario.moviedb.core.model.GenreResponse
import com.mario.moviedb.databinding.ItemGenreBinding

class GenreMovieAdapter : RecyclerView.Adapter<GenreMovieAdapter.GenreMovieViewHolder>() {
    var onClick: ((GenreResponse.Genre) -> Unit)? = null

    private val diffCallBack = object : DiffUtil.ItemCallback<GenreResponse.Genre>() {
        override fun areItemsTheSame(
            oldItem: GenreResponse.Genre,
            newItem: GenreResponse.Genre
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: GenreResponse.Genre,
            newItem: GenreResponse.Genre
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)

    inner class GenreMovieViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenreResponse.Genre) {
            binding.apply {
                tvGenre.text = data.name
                root.setOnClickListener {
                    onClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreMovieViewHolder {
        return GenreMovieViewHolder(
            ItemGenreBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_genre, parent, false
                )
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GenreMovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}