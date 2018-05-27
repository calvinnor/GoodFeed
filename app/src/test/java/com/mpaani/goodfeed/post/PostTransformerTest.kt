package com.mpaani.goodfeed.post

import android.content.Context
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.mock.*
import com.mpaani.goodfeed.post.transformer.getPostViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests the ViewModel transformer for generating Feed View Models.
 */
@RunWith(MockitoJUnitRunner::class)
class PostTransformerTest {

    @Mock
    lateinit var appContext: Context

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun verifyPostViewModelForValidUser() {
        val fakePost = getFakePost()
        val fakeUser = getFakeUser()
        val postViewModel = getPostViewModel(appContext, fakeUser, fakePost)

        assert(postViewModel.postAuthor == fakeUser.name)
        assert(postViewModel.postAuthorCompany == fakeUser.company.name)
        assert(postViewModel.userEmail == fakeUser.email)
        assert(postViewModel.postTitle == fakePost.title)
        assert(postViewModel.postDesc == fakePost.body)
        assert(postViewModel.postId == fakePost.id)
    }

    @Test
    fun verifyPostViewModelForAnonymousUser() {
        `when`(appContext.getString(R.string.feed_anonymous_user_name)).thenReturn(ANONYMOUS_NAME)
        `when`(appContext.getString(R.string.feed_anonymous_user_company)).thenReturn(ANONYMOUS_COMPANY)
        `when`(appContext.getString(R.string.feed_anonymous_user_email)).thenReturn(ANONYMOUS_EMAIL)

        val fakePost = getFakePost()
        val postViewModel = getPostViewModel(appContext, null, fakePost)

        assert(postViewModel.postAuthor == ANONYMOUS_NAME)
        assert(postViewModel.postAuthorCompany == ANONYMOUS_COMPANY)
        assert(postViewModel.userEmail == ANONYMOUS_EMAIL)
        assert(postViewModel.postTitle == fakePost.title)
        assert(postViewModel.postDesc == fakePost.body)
        assert(postViewModel.postId == fakePost.id)
    }
}
