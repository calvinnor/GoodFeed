package com.mpaani.goodfeed.post.transformer

import android.content.Context
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.post.viewmodel.PostViewModel

/**
 * Convert the given user & feed model into Post View Model which can be used for display.
 */
fun getPostViewModel(context: Context, userModel: User?, postModel: Post): PostViewModel {

    var postAuthor = userModel?.name
    var postCompany = userModel?.company?.name
    var userId = userModel?.email

    if (postAuthor == null) postAuthor = context.getString(R.string.feed_anonymous_user_name)
    if (postCompany == null) postCompany = context.getString(R.string.feed_anonymous_user_company)
    if (userId == null) userId = context.getString(R.string.feed_anonymous_user_email)

    return PostViewModel(postModel.id, userId!!, postModel.title, postModel.body, postAuthor!!, postCompany!!)
}
