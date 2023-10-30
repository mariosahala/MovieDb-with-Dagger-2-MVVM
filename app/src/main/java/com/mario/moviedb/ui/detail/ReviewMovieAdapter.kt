package com.mario.moviedb.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mario.moviedb.R
import com.mario.moviedb.core.constant.Constants
import com.mario.moviedb.core.extention.loadImage
import com.mario.moviedb.core.model.ReviewMovieResponse
import com.mario.moviedb.databinding.ItemReviewMovieBinding

class ReviewMovieAdapter : RecyclerView.Adapter<ReviewMovieAdapter.ReviewMovieViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<ReviewMovieResponse.Result>() {
        override fun areItemsTheSame(
            oldItem: ReviewMovieResponse.Result,
            newItem: ReviewMovieResponse.Result
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: ReviewMovieResponse.Result,
            newItem: ReviewMovieResponse.Result
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallBack)

    inner class ReviewMovieViewHolder(private val binding: ItemReviewMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewMovieResponse.Result) {
            binding.apply {
                tvNameReview.text = data.author
                tvDescriptionReview.text = data.content
                ivIconUserReview.loadImage(Constants.IMAGE_BASE + data.authorDetails.avatarPath)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewMovieViewHolder {
        return ReviewMovieViewHolder(
            ItemReviewMovieBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_review_movie, parent, false
                )
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ReviewMovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}