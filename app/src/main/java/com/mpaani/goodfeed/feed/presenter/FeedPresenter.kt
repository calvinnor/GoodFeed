package com.mpaani.goodfeed.feed.presenter

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.data.ApiResponse
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.core.db.DataProxy
import com.mpaani.goodfeed.core.event.Events
import com.mpaani.goodfeed.core.injection.dependencyComponent
import com.mpaani.goodfeed.feed.FeedPresenterContract
import com.mpaani.goodfeed.feed.FeedViewContract
import com.mpaani.goodfeed.feed.event.PostsEvent
import com.mpaani.goodfeed.feed.event.UsersEvent
import com.mpaani.goodfeed.feed.transformer.getFeedViewModels
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Presenter logic for Feeds.
 * This class internally handles API access, DB access and presenting to the UI.
 */
class FeedPresenter : FeedPresenterContract {

    @Inject
    protected lateinit var apiProxy: ApiProxy

    @Inject
    protected lateinit var dataProxy: DataProxy

    @Inject
    protected lateinit var appContext: Context

    private var feedView: WeakReference<FeedViewContract>
    private val postItems: MutableList<Post> = ArrayList()
    private val userList: MutableList<User> = ArrayList()

    private var fetchedUsersFromServer = false
    private var fetchedPostsFromServer = false

    constructor(feedViewContract: FeedViewContract) {
        dependencyComponent.inject(this)

        feedView = WeakReference<FeedViewContract>(feedViewContract)
        Events.subscribe(this)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    constructor(feedViewContract: FeedViewContract,
                apiProxy: ApiProxy,
                dataProxy: DataProxy,
                appContext: Context) {

        this.apiProxy = apiProxy
        this.dataProxy = dataProxy
        this.appContext = appContext

        feedView = WeakReference<FeedViewContract>(feedViewContract)
        Events.subscribe(this)
    }

    override fun fetchItems() {
        fetchedUsersFromServer = false
        fetchedPostsFromServer = false

        fetchPosts(returnCached = true)
        fetchUsers(returnCached = true)
    }

    override fun forceRefreshItems() {
        fetchedUsersFromServer = false
        fetchedPostsFromServer = false

        fetchPosts(returnCached = false)
        fetchUsers(returnCached = false)
    }

    override fun postClicked(feedViewModel: FeedViewModel) {
        feedView()?.onNavigateToPost(feedViewModel.postId, feedViewModel.postAuthor, feedViewModel.userEmail)
    }

    override fun onExit() {
        Events.unsubscribe(this)
    }

    private fun fetchUsers(returnCached: Boolean) {
        if (returnCached) dataProxy.getUsers()

        // Try to fetch from API
        apiProxy.getUsers(object : ApiResponse<List<User>>() {

            override fun onSuccess(response: List<User>?) {
                if (response == null) {
                    feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
                    return
                }

                fetchedUsersFromServer = true
                populateUserList(response)
                cacheUsers()
                convertToViewModels()
            }

            override fun onFailure(throwable: Throwable) {
                feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
            }
        })
    }

    private fun fetchPosts(returnCached: Boolean) {
        if (returnCached) dataProxy.getPosts()

        // Try to fetch from API
        apiProxy.getPosts(object : ApiResponse<List<Post>>() {

            override fun onSuccess(response: List<Post>?) {
                if (response == null) {
                    feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
                    return
                }

                fetchedPostsFromServer = true
                populatePostList(response)
                cachePosts()
                convertToViewModels()
            }

            override fun onFailure(throwable: Throwable) {
                feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
            }
        })
    }

    private fun populateUserList(users: List<User>) {
        userList.apply {
            clear()
            addAll(users)
        }
    }

    private fun populatePostList(posts: List<Post>) {
        postItems.apply {
            clear()
            addAll(posts)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUsersReceived(usersEvent: UsersEvent) {
        Events.removeSticky(usersEvent)
        if (fetchedUsersFromServer) return
        if (usersEvent.userList.isEmpty()) return
        populateUserList(usersEvent.userList)
        convertToViewModels()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPostsReceived(postsEvent: PostsEvent) {
        Events.removeSticky(postsEvent)
        if (fetchedPostsFromServer) return
        if (postsEvent.postsList.isEmpty()) return
        populatePostList(postsEvent.postsList)
        convertToViewModels()
    }

    fun convertToViewModels() {
        if (postItems.isEmpty() || userList.isEmpty()) return

        val feedViewModels = getFeedViewModels(appContext, postItems, userList)
        feedView()?.onFeedItemsReceived(feedViewModels)
    }

    private fun feedView(): FeedViewContract? = feedView.get()

    private fun cacheUsers() = dataProxy.insertUsers(userList)

    private fun cachePosts() = dataProxy.insertPosts(postItems)
}
