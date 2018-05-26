package com.mpaani.goodfeed.post

import com.mpaani.goodfeed.core.contract.BasePresenterContract
import com.mpaani.goodfeed.post.viewmodel.CommentViewModel
import com.mpaani.goodfeed.post.viewmodel.PostViewModel

interface PostViewContract {

    /**
     * When we have successfully built the Post
     */
    fun onPostReceived(postViewModel: PostViewModel)

    /**
     * When we can show the Comments list.
     */
    fun onCommentsReceived(commentViewModels: List<CommentViewModel>)

    /**
     * When there was some error while processing posts.
     * The reason is a user-readable input.
     */
    fun onError(reason: String)
}

interface PostPresenterContract : BasePresenterContract {

    /**
     * Fetches the given post.
     */
    fun fetchPost()

    /**
     * Fetch the comments for the post.
     */
    fun fetchComments()

}
