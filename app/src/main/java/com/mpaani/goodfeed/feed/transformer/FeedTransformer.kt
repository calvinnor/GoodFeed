package com.mpaani.goodfeed.feed.transformer

import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel
import java.util.*

/**
 * Convert the given lists into Feed View Models which can be used for display.
 */
fun getFeedViewModels(postItems: List<Post>, userList: List<User>): List<FeedViewModel> {
    val feedViewModels: MutableList<FeedViewModel> = ArrayList()

    for (postItem in postItems) {
        val userForPost = userList.find { it.id == postItem.id }

        var postAuthor = userForPost?.name
        var postCompany = userForPost?.company?.name
        var userId = userForPost?.email

        // TODO: Handle this error, for now use dummy data
        if (postAuthor == null) postAuthor = "Anonymous"
        if (postCompany == null) postCompany = "Anonymous Ltd"
        if (userId == null) userId = userList[0].email

        feedViewModels.add(FeedViewModel(postItem.id, userId, postItem.title, postItem.body, postAuthor, postCompany))
    }
    return feedViewModels
}
