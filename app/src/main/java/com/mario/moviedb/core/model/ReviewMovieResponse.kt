package com.mario.moviedb.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ReviewMovieResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: ArrayList<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) : Serializable {
    data class Result(
        @SerializedName("author")
        val author: String,
        @SerializedName("author_details")
        val authorDetails: AuthorDetails,
        @SerializedName("content")
        val content: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("url")
        val url: String
    ) : Serializable {
        data class AuthorDetails(
            @SerializedName("avatar_path")
            val avatarPath: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("rating")
            val rating: Any,
            @SerializedName("username")
            val username: String
        ) : Serializable
    }
}