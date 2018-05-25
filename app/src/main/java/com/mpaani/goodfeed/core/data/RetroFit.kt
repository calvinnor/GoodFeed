package com.mpaani.goodfeed.core.data

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

    override fun getPosts() = apiInterface.fetchPosts()

    override fun getUsers() = apiInterface.fetchUsers()

    override fun getComments() = apiInterface.fetchComments()

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(FEED_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
    }
}
