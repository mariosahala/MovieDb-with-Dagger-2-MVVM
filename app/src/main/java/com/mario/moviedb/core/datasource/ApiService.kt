package com.mario.moviedb.core.datasource

import com.mario.moviedb.core.model.DetailMovieResponse
import com.mario.moviedb.core.model.GenreResponse
import com.mario.moviedb.core.model.ListMovieResponse
import com.mario.moviedb.core.model.ReviewMovieResponse
import com.mario.moviedb.core.model.VideoTrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/genre/movie/list?api_key=be8b6c8aa9a5f4e240bb6093f9849051")
    suspend fun getGenreList(): Response<GenreResponse>

    @GET("3/discover/movie?api_key=be8b6c8aa9a5f4e240bb6093f9849051")
    suspend fun getMovieList(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<ListMovieResponse>

    @GET("3/movie/{movie_id}?api_key=be8b6c8aa9a5f4e240bb6093f9849051")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int
    ): Response<DetailMovieResponse>

    @GET("3/movie/{movie_id}/reviews?api_key=be8b6c8aa9a5f4e240bb6093f9849051")
    suspend fun getReviewMovie(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<ReviewMovieResponse>

    @GET("3/movie/{movie_id}/videos?api_key=be8b6c8aa9a5f4e240bb6093f9849051")
    suspend fun getVideoTrailer(@Path("movie_id") movieId: Int): Response<VideoTrailerResponse>
}