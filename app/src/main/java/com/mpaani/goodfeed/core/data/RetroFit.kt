package com.mpaani.goodfeed.core.data

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Retrofit singleton for accessing APIs.
 *
 * Clients should never use this class directly, rather communicate via a Proxy.
 */
object RetroFit : ApiProxy {

    private val retroFitInstance: Retrofit

    // Endpoint Interfaces
    private val apiInterface: ApiInterface

    init {
        retroFitInstance = buildRetrofit()
        apiInterface = retroFitInstance.create(ApiInterface::class.java)
    }

    override fun getPosts(apiResponse: ApiResponse<List<Post>>) {
        apiInterface.fetchPosts().enqueue(apiResponse)
    }

    override fun getUsers(apiResponse: ApiResponse<List<User>>) {
        apiInterface.fetchUsers().enqueue(apiResponse)
    }

    override fun getComments(apiResponse: ApiResponse<List<Comment>>) {
        apiInterface.fetchComments().enqueue(apiResponse)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(FEED_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
    }
}
