package com.mpaani.goodfeed.feed.transformer

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel
import java.util.*

/**
 * Convert the given lists into Feed View Models which can be used for display.
 */
fun getFeedViewModels(context: Context, postItems: List<Post>, userList: List<User>): List<FeedViewModel> {
    val feedViewModels: MutableList<FeedViewModel> = ArrayList()

    postItems.forEach { feedViewModels.add(getFeedViewModel(context, it, userList)) }
    return feedViewModels
}

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun getFeedViewModel(context: Context, postItem: Post, userList: List<User>): FeedViewModel {
    val userForPost = userList.find { it.id == postItem.userId }

    var postAuthor = userForPost?.name
    var postCompany = userForPost?.company?.name
    var userId = userForPost?.email

    if (postAuthor == null) postAuthor = context.getString(R.string.feed_anonymous_user_name)
    if (postCompany == null) postCompany = context.getString(R.string.feed_anonymous_user_company)
    if (userId == null) userId = context.getString(R.string.feed_anonymous_user_email)

    return FeedViewModel(postItem.id, userId!!, postItem.title, postItem.body, postAuthor!!, postCompany!!)
}
