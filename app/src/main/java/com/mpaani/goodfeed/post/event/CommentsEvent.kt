package com.mpaani.goodfeed.post.event

import com.mpaani.goodfeed.core.data.model.Comment

/**
 * Event to be posted when Comments are retrieved from DB.
 */
data class CommentsEvent(val commentsList: List<Comment>)
