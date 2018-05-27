package com.mpaani.goodfeed.post.viewmodel

import android.arch.lifecycle.ViewModel

/**
 * In-memory cache for Post.
 * Will be persisted across configuration changes.
 */
class PostViewCache : ViewModel() {

    private var postViewModel: PostViewModel? = null
    private var commentViewModels: List<CommentViewModel>? = null

    /**
     * Cache the Post View Model.
     */
    fun cachePost(postViewModel: PostViewModel) {
        this.postViewModel = postViewModel
    }

    /**
     * Cache the comments View Models.
     */
    fun cacheComments(commentViewModels: List<CommentViewModel>) {
        this.commentViewModels = commentViewModels
    }

    /**
     * Returns true if we have cached the Post view model.
     */
    fun hasPostData() = postViewModel != null

    /**
     * Returns true if we have cached any Comments ViewModel data.
     */
    fun hasCommentsData(): Boolean {
        commentViewModels.let {
            return it != null && it.isNotEmpty()
        }
    }

    /**
     * Get the Comments View Models.
     */
    fun getComments() = commentViewModels

    /**
     * Get the Post View Model.
     */
    fun getPost() = postViewModel
}
