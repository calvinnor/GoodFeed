package com.mpaani.goodfeed.core.data

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import retrofit2.Call
import retrofit2.http.GET

/**
 * API endpoints for the app.
 */
interface ApiInterface {

    @GET(POSTS_ENDPOINT)
    fun fetchPosts(): Call<List<Post>>

    @GET(USERS_ENDPOINT)
    fun fetchUsers(): Call<List<User>>

    @GET(COMMENTS_ENDPOINT)
    fun fetchComments(): Call<List<Comment>>
}
