package com.mpaani.goodfeed.feed.presenter

import android.content.Context
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.ApiProxy
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Presenter logic for Feeds.
 * This class internally handles API access, DB access and presenting to the UI.
 */
class FeedPresenter(feedViewContract: FeedViewContract) : FeedPresenterContract {

    @Inject
    protected lateinit var apiProxy: ApiProxy

    @Inject
    protected lateinit var dataProxy: DataProxy

    @Inject
    protected lateinit var appContext: Context

    private val feedView: WeakReference<FeedViewContract>
    private val postItems: MutableList<Post> = ArrayList()
    private val userList: MutableList<User> = ArrayList()

    private var fetchFromServerComplete = false

    init {
        dependencyComponent.inject(this)
        feedView = WeakReference<FeedViewContract>(feedViewContract)

        Events.subscribe(this)
    }

    override fun fetchItems() {
        fetchPosts()
        fetchUsers()
    }

    override fun postClicked(feedViewModel: FeedViewModel) {
        feedView()?.onNavigateToPost(feedViewModel.postId, feedViewModel.postAuthor, feedViewModel.userEmail)
    }

    override fun onExit() {
        Events.unsubscribe(this)
    }

    private fun fetchUsers() {
        // Fetch from DB first
        dataProxy.getUsers()

        // Try to fetch from API
        apiProxy.getUsers().enqueue(object : Callback<List<User>> {

            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
            }

            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                if (response?.body() == null) {
                    feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
                    return
                }

                val usersList = response.body()!!

                populateUserList(usersList)
                cacheUsers()
                convertToViewModels()
            }
        })
    }

    private fun fetchPosts() {
        // Fetch from DB first
        dataProxy.getPosts()

        // Try to fetch from API
        apiProxy.getPosts().enqueue(object : Callback<List<Post>> {

            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
            }

            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                if (response?.body() == null) {
                    feedView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
                    return
                }

                val postsList = response.body()!!

                populatePostList(postsList)
                cachePosts()
                convertToViewModels()
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

    @Subscribe
    fun onUsersReceived(usersEvent: UsersEvent) {
        Events.removeSticky(usersEvent)
        if (fetchFromServerComplete) return
        if (usersEvent.userList.isEmpty()) return
        populateUserList(usersEvent.userList)
        convertToViewModels()
    }

    @Subscribe
    fun onPostsReceived(postsEvent: PostsEvent) {
        Events.removeSticky(postsEvent)
        if (fetchFromServerComplete) return
        if (postsEvent.postsList.isEmpty()) return
        populatePostList(postsEvent.postsList)
        convertToViewModels()
    }

    fun convertToViewModels() {
        if (postItems.isEmpty() || userList.isEmpty()) return

        val feedViewModels = getFeedViewModels(postItems, userList)
        feedView()?.onFeedItemsReceived(feedViewModels)
    }

    private fun feedView(): FeedViewContract? = feedView.get()

    private fun cacheUsers() = dataProxy.insertUsers(userList)

    private fun cachePosts() = dataProxy.insertPosts(postItems)
}