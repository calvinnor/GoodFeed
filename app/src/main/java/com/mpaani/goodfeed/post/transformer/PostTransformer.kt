package com.mpaani.goodfeed.post.transformer

import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.post.viewmodel.PostViewModel

/**
 * Convert the given user & feed model into Post View Model which can be used for display.
 */
fun getPostViewModel(userModel: User, postModel: Post): PostViewModel {

    var postAuthor = userModel.name
    var postCompany = userModel.company?.name
    val userId = userModel.email

    // TODO: Handle this error, for now use dummy data
    if (postAuthor == null) postAuthor = "Anonymous"
    if (postCompany == null) postCompany = "Anonymous Ltd"

    return PostViewModel(postModel.id, userId, postModel.title, postModel.body, postAuthor, postCompany)
}
