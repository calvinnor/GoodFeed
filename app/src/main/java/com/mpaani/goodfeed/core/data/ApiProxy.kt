package com.mpaani.goodfeed.core.data

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User

/**
 * Interface to hide API implementations from UI clients.
 */
interface ApiProxy {

    /**
     * Get the posts from server.
     */
    fun getPosts(apiResponse: ApiResponse<List<Post>>)

    /**
     * Get all users from server.
     */
    fun getUsers(apiResponse: ApiResponse<List<User>>)

    /**
     * Get all post's comments from server.
     */
    fun getComments(apiResponse: ApiResponse<List<Comment>>)

}
