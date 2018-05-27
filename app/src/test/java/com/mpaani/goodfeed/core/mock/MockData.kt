package com.mpaani.goodfeed.core.mock

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Company
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel

const val POST_ID = 1
const val POST_TITLE = "post title"
const val POST_DESC = "post desc"
const val POST_AUTHOR = "post author"
const val POST_COMPANY = "post company"

const val USER_ID = 1
const val USER_EMAIL = "user@email.com"
const val USER_NAME = "UserName"

const val COMMENT_ID = 1
const val COMMENT_NAME = "Some Name"
const val COMMENT_EMAIL = "abcde@gmail.com"
const val COMMENT_BODY = "Some long comment"

const val ANONYMOUS_NAME = "Anonymous"
const val ANONYMOUS_EMAIL = "anony@gmail.com"
const val ANONYMOUS_COMPANY = "Company Ltd"

fun getFakeFeedViewModel() = FeedViewModel(POST_ID, USER_EMAIL, POST_TITLE, POST_DESC, POST_AUTHOR, POST_COMPANY)

fun getFakePost() = Post().apply {
    userId = USER_ID
    id = POST_ID
    title = POST_TITLE
    body = POST_DESC
}

fun getFakeUser() = User().apply {
    id = USER_ID
    email = USER_EMAIL
    name = POST_AUTHOR
    company = Company().apply {
        name = POST_COMPANY
    }
    username = USER_NAME
}

fun getFakeComment() = Comment().apply {
    postId = POST_ID
    id = COMMENT_ID
    name = COMMENT_NAME
    email = COMMENT_EMAIL
    body = COMMENT_BODY
}
