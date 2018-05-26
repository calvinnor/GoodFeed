package com.mpaani.goodfeed.feed.viewmodel

/**
 * Data class for adapters and Feed providers.
 */
data class FeedViewModel(val postId: Int,
                         val userEmail: String,
                         val postTitle: String,
                         val postDesc: String,
                         val postAuthor: String,
                         val postAuthorCompany: String)
