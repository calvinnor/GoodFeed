package com.mpaani.goodfeed.core.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Encapsulation for API callbacks.
 *
 * Internally uses RetroFit's architecture, but can be hot-swapped for
 * something else.
 */
abstract class ApiResponse<T> : Callback<T> {

    companion object {

        private const val HTTP_STATUS_CODE_OK = 200
    }

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        onFailure(t!!)
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        if (response?.body() == null) {
            onFailure(Throwable("Null response from server"))
            return
        }

        if (response.code() == HTTP_STATUS_CODE_OK) onSuccess(response.body())
        else onFailure(Throwable("Status code: " + response.code()))
    }

    /**
     * Called when we receive a Successful response.
     */
    abstract fun onSuccess(response: T?)

    /**
     * Called when we receive a failure.
     */
    abstract fun onFailure(throwable: Throwable)

    override fun equals(other: Any?) = other != null && other is ApiResponse<*>

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
