package com.mpaani.goodfeed.feed

import android.content.Context
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.db.DataProxy
import com.mpaani.goodfeed.core.event.Events
import com.mpaani.goodfeed.core.mock.*
import com.mpaani.goodfeed.feed.event.PostsEvent
import com.mpaani.goodfeed.feed.event.UsersEvent
import com.mpaani.goodfeed.feed.presenter.FeedPresenter
import com.mpaani.goodfeed.feed.transformer.getFeedViewModels
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests the Presenter for calling methods & correct behavior.
 */
@RunWith(MockitoJUnitRunner::class)
class FeedPresenterTest {

    @Mock
    private lateinit var dataProxy: DataProxy

    @Mock
    private lateinit var apiProxy: ApiProxy

    @Mock
    private lateinit var feedView: FeedViewContract

    @Mock
    private lateinit var appContext: Context

    private lateinit var feedPresenter: FeedPresenter

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        feedPresenter = FeedPresenter(feedView, apiProxy, dataProxy, appContext)
    }

    @Test
    fun verifyThatFeedItemClickNavigatesToPost() {
        val feedViewModel = getFakeFeedViewModel()
        feedPresenter.postClicked(feedViewModel)
        verify(feedView).onNavigateToPost(POST_ID, POST_AUTHOR, USER_EMAIL)
    }

    @Test
    fun verifyThatFetchItemsReadsFromDB() {
        feedPresenter.fetchItems()
        verify(dataProxy).getUsers()
        verify(dataProxy).getPosts()
    }

    @Test
    fun verifyThatFetchItemsCallsApis() {
        feedPresenter.fetchItems()
        verify(apiProxy).getUsers(MockApiResponse())
        verify(apiProxy).getPosts(MockApiResponse())
    }

    @Test
    fun verifyThatForceRefreshCallsApis() {
        feedPresenter.forceRefreshItems()
        verify(apiProxy).getUsers(MockApiResponse())
        verify(apiProxy).getPosts(MockApiResponse())
    }

    @Test
    fun verifyThatForceRefreshDoesNotUseCache() {
        feedPresenter.forceRefreshItems()
        verify(dataProxy, never()).getUsers()
        verify(dataProxy, never()).getPosts()
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
