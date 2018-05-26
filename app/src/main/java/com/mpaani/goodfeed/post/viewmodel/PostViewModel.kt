package com.mpaani.goodfeed.post.viewmodel

/**
 * Data class for Posts UI.
 */
data class PostViewModel(val postId: Int,
                         val userEmail: String,
                         val postTitle: String,
                         val postDesc: String,
                         val postAuthor: String,
                         val postAuthorCompany: String)
