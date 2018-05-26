package com.mpaani.goodfeed.post.event

import com.mpaani.goodfeed.core.data.model.Post

/**
 * Event to be posted when a Post is loaded from DB.
 */
data class PostEvent(val post: Post)
