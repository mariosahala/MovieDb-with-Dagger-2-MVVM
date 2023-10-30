package com.mario.moviedb.core.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GenreResponse(
    @SerializedName("genres") var genres: ArrayList<Genre> = arrayListOf()
) : Serializable {
    data class Genre(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        var name: String = ""
    )
}