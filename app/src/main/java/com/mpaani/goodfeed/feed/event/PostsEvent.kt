package com.mpaani.goodfeed.feed.event

import com.mpaani.goodfeed.core.data.model.Post

/**
 * Event to be posted when Posts have been loaded from DB.
 */
data class PostsEvent(val postsList: List<Post>)
