package com.mpaani.goodfeed.feed

import android.content.Context
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.mock.getFakePost
import com.mpaani.goodfeed.core.mock.getFakeUser
import com.mpaani.goodfeed.feed.transformer.getFeedViewModel
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
class FeedTransformerTest {

    companion object {
        private const val ANONYMOUS_NAME = "Anonymous"
        private const val ANONYMOUS_EMAIL = "anony@gmail.com"
        private const val ANONYMOUS_COMPANY = "Company Ltd"
    }

    @Mock
    lateinit var appContext: Context

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun verifyFeedViewModelForValidUser() {
        val fakePost = getFakePost()
        val fakeUser = getFakeUser()
        val feedViewModel = getFeedViewModel(appContext, fakePost, listOf(fakeUser))

        assert(feedViewModel.postAuthor == fakeUser.name)
        assert(feedViewModel.postAuthorCompany == fakeUser.company.name)
        assert(feedViewModel.userEmail == fakeUser.email)
        assert(feedViewModel.postTitle == fakePost.title)
        assert(feedViewModel.postDesc == fakePost.body)
        assert(feedViewModel.postId == fakePost.id)
    }

    @Test
    fun verifyFeedViewModelForAnonymousUser() {
        `when`(appContext.getString(R.string.feed_anonymous_user_name)).thenReturn(ANONYMOUS_NAME)
        `when`(appContext.getString(R.string.feed_anonymous_user_company)).thenReturn(ANONYMOUS_COMPANY)
        `when`(appContext.getString(R.string.feed_anonymous_user_email)).thenReturn(ANONYMOUS_EMAIL)

        val fakePost = getFakePost()
        val fakeUser = getFakeUser()
        fakeUser.id = 1001 // Simulate user cannot be found

        val feedViewModel = getFeedViewModel(appContext, fakePost, listOf(fakeUser))

        assert(feedViewModel.postAuthor == ANONYMOUS_NAME)
        assert(feedViewModel.postAuthorCompany == ANONYMOUS_COMPANY)
        assert(feedViewModel.userEmail == ANONYMOUS_EMAIL)
        assert(feedViewModel.postTitle == fakePost.title)
        assert(feedViewModel.postDesc == fakePost.body)
        assert(feedViewModel.postId == fakePost.id)
    }
}
