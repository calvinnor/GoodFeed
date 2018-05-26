package com.mpaani.goodfeed.post.transformer

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.post.viewmodel.CommentViewModel
import java.util.*

/**
 * Convert the given user & feed model into Post View Model which can be used for display.
 */
fun getCommentsViewModels(comments: List<Comment>): List<CommentViewModel> {
    val commentViewModels: MutableList<CommentViewModel> = ArrayList()
    for (comment in comments) {
        commentViewModels.add(CommentViewModel(comment.name, comment.email, comment.body))
    }
    return commentViewModels
}
