package com.mpaani.goodfeed.feed

import android.content.Context
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.db.DataProxy
import com.mpaani.goodfeed.core.event.Events
import com.mpaani.goodfeed.core.mock.MockCall
import com.mpaani.goodfeed.core.mock.getFakePost
import com.mpaani.goodfeed.core.mock.getFakeUser
import com.mpaani.goodfeed.feed.event.PostsEvent
import com.mpaani.goodfeed.feed.event.UsersEvent
import com.mpaani.goodfeed.feed.presenter.FeedPresenter
import com.mpaani.goodfeed.feed.transformer.getFeedViewModels
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests the Presenter for calling methods & correct behavior.
 */
@RunWith(MockitoJUnitRunner::class)
class FeedPresenterTest {

    companion object {
        private const val POST_ID = 1
        private const val USER_EMAIL = "user@email.com"
        private const val POST_TITLE = "post title"
        private const val POST_DESC = "post desc"
        private const val POST_AUTHOR = "post author"
        private const val POST_COMPANY = "post company"
    }

    @Mock
    lateinit var dataProxy: DataProxy

    @Mock
    lateinit var apiProxy: ApiProxy

    @Mock
    lateinit var feedView: FeedViewContract

    @Mock
    lateinit var appContext: Context

    private lateinit var feedPresenter: FeedPresenter

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        feedPresenter = FeedPresenter(feedView, apiProxy, dataProxy, appContext)
    }

    @Test
    fun verifyThatFeedItemClickNavigatesToPost() {
        val feedViewModel = FeedViewModel(POST_ID, USER_EMAIL, POST_TITLE, POST_DESC, POST_AUTHOR, POST_COMPANY)
        feedPresenter.postClicked(feedViewModel)
        verify(feedView).onNavigateToPost(POST_ID, POST_AUTHOR, USER_EMAIL)
    }

    @Test
    fun verifyThatFetchItemsReadsFromDB() {
        `when`(apiProxy.getUsers()).thenReturn(MockCall())
        `when`(apiProxy.getPosts()).thenReturn(MockCall())

        feedPresenter.fetchItems()
        verify(dataProxy).getUsers()
        verify(dataProxy).getPosts()
    }

    @Test
    fun verifyThatFetchItemsCallsApis() {
        `when`(apiProxy.getUsers()).thenReturn(MockCall())
        `when`(apiProxy.getPosts()).thenReturn(MockCall())

        feedPresenter.fetchItems()
        verify(apiProxy).getUsers()
        verify(apiProxy).getPosts()
    }

    @Test
    fun verifyThatViewReceivesFeedViewModels() {
        fakeUsersAndPosts()
        verify(feedView).onFeedItemsReceived(generateFeedViewModels())
    }

    private fun generateFeedViewModels() =
            getFeedViewModels(appContext, listOf(getFakePost()), listOf(getFakeUser()))

    private fun fakeUsersAndPosts() {
        Events.post(UsersEvent(listOf(getFakeUser())))
        Events.post(PostsEvent(listOf(getFakePost())))
    }
}
