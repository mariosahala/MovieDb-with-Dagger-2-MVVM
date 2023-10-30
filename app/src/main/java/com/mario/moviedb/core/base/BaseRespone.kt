package com.mario.moviedb.core.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("entity") val entity: String,
    @SerializedName("state") val state: String,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: T?,
    @SerializedName("errors") val errors: ArrayList<String>?
)