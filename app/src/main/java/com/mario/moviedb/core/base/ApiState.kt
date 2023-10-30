package com.mario.moviedb.core.base

import okhttp3.HttpUrl
import retrofit2.Response

data class ApiState<out T>(
    val status: State,
    val data: T?,
    val message: String?,
    val code: Int?,
    val response: okhttp3.Response? = null,
    val errorData: String? = null,
    val state: String? = null,
    val httpUrl: HttpUrl? = null,
    val errorResponse: String? = null,
) {

    companion object {
        fun <T> success(data: T?, response: okhttp3.Response? = null): ApiState<T> {
            return ApiState(State.SUCCESS, data, null, 200, response)
        }

        fun <T> error(
            msg: String,
            data: T?,
            code: Int?,
            response: okhttp3.Response? = null,
            errorData: String? = null,
            state: String? = null,
            httpUrl: HttpUrl? = null,
            errorResponse: String? = null
        ): ApiState<T> {
            return ApiState(
                State.ERROR,
                data,
                msg,
                code,
                response,
                errorData,
                state,
                httpUrl,
                errorResponse
            )
        }

        fun <T> loading(): ApiState<T> {
            return ApiState(State.LOADING, null, null, 200)
        }

        fun <T> returnData(data: Response<T>): ApiState<T> {
            return success(data.body(), data.raw())
        }
    }
}

//data class ApiState<out T>(val status: State, val data: T?, val message: String?, val code: Int?) {
//
//    companion object {
//
//        fun <T> success(data: T?): ApiState<T> {
//            return ApiState(State.SUCCESS, data, null, 200)
//        }
//
//        fun <T> error(msg: String, data: T?, code: Int?): ApiState<T> {
//            return ApiState(State.ERROR, data, msg, code)
//        }
//
//        fun <T> loading(): ApiState<T> {
//            return ApiState(State.LOADING, null, null, 200)
//        }
//    }
//}