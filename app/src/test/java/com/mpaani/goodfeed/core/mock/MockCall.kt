package com.mpaani.goodfeed.core.mock

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MockCall<T> : Call<T> {

    override fun enqueue(callback: Callback<T>?) {

    }

    override fun isExecuted() = true

    override fun clone(): Call<T> = MockCall()

    override fun isCanceled() = false

    override fun cancel() {}

    override fun execute(): Response<T> {
        TODO("not implemented")
    }

    override fun request(): Request {
        TODO("not implemented")
    }
}
