package com.mpaani.goodfeed.core.mock

import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Company
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User

fun getFakePost() = Post().apply {
    userId = 1
    id = 5
    title = "Title"
    body = "Body"
}

fun getFakeUser() = User().apply {
    id = 1
    email = "Sincere@april.biz"
    name = "Test User"
    company = Company().apply {
        name = "Company"
    }
    username = "UserName"
}

fun getFakeComment() = Comment().apply {
    postId = 1
    id = 5
    name = "Some Name"
    email = "abcde@gmail.com"
    body = "Some long comment"
}
