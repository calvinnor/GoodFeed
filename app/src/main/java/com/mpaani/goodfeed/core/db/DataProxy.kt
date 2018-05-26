package com.mpaani.goodfeed.core.db

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User

/**
 * Abstraction layer for clients to communicate with the Task data source.
 *
 * Always use this interface for communication, and not the classes directly that implement it.
 */
interface DataProxy {

    /**
     * Insert users into DB.
     */
    fun insertUsers(usersList: List<User>)

    /**
     * Insert posts into DB.
     */
    fun insertPosts(postsList: List<Post>)

    /**
     * Insert comments into DB.
     */
    fun insertComments(commentsList: List<Comment>)

    /**
     * Get the list of users.
     */
    fun getUsers()

    /**
     * Get the list of posts.
     */
    fun getPosts()

    /**
     * Get the list of comments.
     */
    fun getComments()

    /**
     * Get a post given it's ID
     */
    fun getPostById(postId: Int)

    /**
     * Get a user given his email ID.
     */
    fun getUserByEmail(userEmail: String)
}
