package com.mpaani.goodfeed.post.presenter

import android.content.Context
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.data.model.Comment
import com.mpaani.goodfeed.core.data.model.Post
import com.mpaani.goodfeed.core.data.model.User
import com.mpaani.goodfeed.core.db.DataProxy
import com.mpaani.goodfeed.core.event.Events
import com.mpaani.goodfeed.core.injection.dependencyComponent
import com.mpaani.goodfeed.post.PostPresenterContract
import com.mpaani.goodfeed.post.PostViewContract
import com.mpaani.goodfeed.post.event.CommentsEvent
import com.mpaani.goodfeed.post.event.PostEvent
import com.mpaani.goodfeed.post.event.UserEvent
import com.mpaani.goodfeed.post.transformer.getCommentsViewModels
import com.mpaani.goodfeed.post.transformer.getPostViewModel
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

/**
 * Presenter logic for Posts.
 * This class internally handles API access, DB access and presenting to the UI.
 */
class PostPresenter(postViewContract: PostViewContract,
                    private val postId: Int,
                    private val userEmail: String) : PostPresenterContract {

    @Inject
    protected lateinit var apiProxy: ApiProxy

    @Inject
    protected lateinit var dataProxy: DataProxy

    @Inject
    protected lateinit var appContext: Context

    private val postView: WeakReference<PostViewContract>
    private var userModel: User? = null
    private var postModel: Post? = null
    private var commentsList: MutableList<Comment> = ArrayList()

    private var fetchFromServerComplete = false

    init {
        dependencyComponent.inject(this)
        postView = WeakReference<PostViewContract>(postViewContract)

        Events.subscribe(this)
    }

    override fun fetchPost() {
        dataProxy.getPostById(postId)
        dataProxy.getUserByEmail(userEmail)
    }

    override fun fetchComments() {
        internalFetchComments()
    }

    override fun onExit() {
        Events.unsubscribe(this)
    }

    private fun internalFetchComments() {
        // Fetch from DB first
        dataProxy.getComments()

        // Try to fetch from API
        apiProxy.getComments().enqueue(object : Callback<List<Comment>> {

            override fun onFailure(call: Call<List<Comment>>?, t: Throwable?) {
                postView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
            }

            override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>?) {
                if (response?.body() == null) {
                    postView()?.onError(appContext.getString(R.string.feed_cannot_fetch_api))
                    return
                }

                fetchFromServerComplete = true

                val commentsList = response.body()!!
                dataProxy.insertComments(commentsList) // Cache

                val commentsForThisPost = getCommentsForThisPost(commentsList)
                populateCommentsList(commentsForThisPost)
                convertToCommentsViewModel()
            }
        })
    }

    private fun populateCommentsList(comments: List<Comment>) {
        commentsList.apply {
            clear()
            addAll(comments)
        }
    }

    @Subscribe
    fun onUserReceived(userEvent: UserEvent) {
        Events.removeSticky(userEvent)
        userModel = userEvent.user
        convertToPostViewModel()
    }

    @Subscribe
    fun onPostReceived(postEvent: PostEvent) {
        Events.removeSticky(postEvent)
        postModel = postEvent.post
        convertToPostViewModel()
    }

    @Subscribe
    fun onCommentsReceived(commentsEvent: CommentsEvent) {
        if (fetchFromServerComplete) return

        val commentsForThisPost = getCommentsForThisPost(commentsEvent.commentsList)
        populateCommentsList(commentsForThisPost)
        convertToCommentsViewModel()
    }

    private fun getCommentsForThisPost(commentsList: List<Comment>) = commentsList.filter { it.postId == postId }

    private fun convertToPostViewModel() {
        if (userModel == null || postModel == null) return

        val postViewModel = getPostViewModel(userModel!!, postModel!!)
        postView()?.onPostReceived(postViewModel)
    }

    private fun convertToCommentsViewModel() {
        if (commentsList.isEmpty()) return

        val commentViewModels = getCommentsViewModels(commentsList)
        postView()?.onCommentsReceived(commentViewModels)
    }

    private fun postView(): PostViewContract? = postView.get()

}
