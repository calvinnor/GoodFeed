package com.mpaani.goodfeed.post

import android.content.Context
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.db.DataProxy
import com.mpaani.goodfeed.core.event.Events
import com.mpaani.goodfeed.core.mock.*
import com.mpaani.goodfeed.post.event.CommentsEvent
import com.mpaani.goodfeed.post.event.PostEvent
import com.mpaani.goodfeed.post.event.UserEvent
import com.mpaani.goodfeed.post.presenter.PostPresenter
import com.mpaani.goodfeed.post.transformer.getCommentsViewModels
import com.mpaani.goodfeed.post.transformer.getPostViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Tests the Presenter for calling methods & correct behavior.
 */
@RunWith(MockitoJUnitRunner::class)
class PostPresenterTest {

    @Mock
    private lateinit var dataProxy: DataProxy

    @Mock
    private lateinit var apiProxy: ApiProxy

    @Mock
    private lateinit var postView: PostViewContract

    @Mock
    private lateinit var appContext: Context

    private lateinit var postPresenter: PostPresenter

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        postPresenter = PostPresenter(postView, POST_ID, USER_EMAIL, apiProxy, dataProxy, appContext)
    }

    @Test
    fun verifyThatFetchPostReadsFromDB() {
        postPresenter.fetchPost()
        verify(dataProxy).getPostById(POST_ID)
        verify(dataProxy).getUserByEmail(USER_EMAIL)
    }

    @Test
    fun verifyThatViewReceivesPost() {
        fakeUserAndPost()
        verify(postView).onPostReceived(generatePostViewModel())
    }

    @Test
    fun verifyThatFetchCommentReadsFromDB() {
        postPresenter.fetchComments()
        verify(dataProxy).getComments()
    }

    @Test
    fun verifyThatFetchCommentCallsApi() {
        postPresenter.fetchComments()
        verify(apiProxy).getComments(MockApiResponse())
    }

    @Test
    fun verifyThatViewReceivesCommentViewModels() {
        fakeComments()
        verify(postView).onCommentsReceived(generateCommentsViewModels())
    }

    @Test
    fun verifyThatForceRefreshCallsApis() {
        postPresenter.forceRefreshItems()
        verify(apiProxy).getComments(MockApiResponse())
    }

    @Test
    fun verifyThatForceRefreshDoesNotUseCache() {
        postPresenter.forceRefreshItems()
        verify(dataProxy, Mockito.never()).getComments()
    }

    private fun generatePostViewModel() =
            getPostViewModel(appContext, getFakeUser(), getFakePost())

    private fun generateCommentsViewModels() =
            getCommentsViewModels(listOf(getFakeComment()))

    private fun fakeUserAndPost() {
        Events.post(UserEvent(getFakeUser()))
        Events.post(PostEvent(getFakePost()))
    }

    private fun fakeComments() {
        Events.post(CommentsEvent(listOf(getFakeComment())))
    }
}
