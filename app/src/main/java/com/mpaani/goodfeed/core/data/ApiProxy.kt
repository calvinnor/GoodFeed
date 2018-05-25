package com.mpaani.goodfeed.core.data

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import retrofit2.Call

/**
 * Interface to hide API implementations from UI clients.
 */
interface ApiProxy {

    fun getPosts(): Call<List<Post>>

    fun getUsers(): Call<List<User>>

    fun getComments(): Call<List<Comment>>
}
