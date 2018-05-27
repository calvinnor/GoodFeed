package com.mpaani.goodfeed.post.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.extension.loadAvatar
import com.mpaani.goodfeed.core.extension.setVisible
import com.mpaani.goodfeed.core.ui.BaseFragment
import com.mpaani.goodfeed.post.PostPresenterContract
import com.mpaani.goodfeed.post.PostViewContract
import com.mpaani.goodfeed.post.adapter.CommentsAdapter
import com.mpaani.goodfeed.post.viewmodel.CommentViewModel
import com.mpaani.goodfeed.post.viewmodel.PostViewCache
import com.mpaani.goodfeed.post.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.view_feed_item.*

/**
 * Container UI class for showing a feed Post.
 */
class PostFragment : BaseFragment(), PostViewContract {

    companion object {
        const val TAG = "PostFragment"

        private const val NO_SAVED_POSITION = -1

        private const val POST_SCROLL_STATE = "post_scroll_state"
    }

    override val fragmentTag = TAG
    override val layout = R.layout.fragment_post
    override lateinit var refreshIndicator: SwipeRefreshLayout

    private lateinit var postPresenter: PostPresenterContract
    private lateinit var postViewCache: PostViewCache
    private val commentsAdapter = CommentsAdapter()

    private var postScrollState: Int = NO_SAVED_POSITION

    /**
     * Set this fragment's presenter.
     */
    fun setPostPresenter(postPresenterContract: PostPresenterContract) {
        this.postPresenter = postPresenterContract
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseCache()
        initialiseViews()
        fetchData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(POST_SCROLL_STATE, post_root_scrollview.scrollY)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        postScrollState = savedInstanceState.getInt(POST_SCROLL_STATE)
    }

    override fun onStop() {
        postPresenter.onExit()
        super.onStop()
    }

    override fun onPostReceived(postViewModel: PostViewModel) {
        populatePost(postViewModel)
        cachePostModel(postViewModel)
    }

    private fun populatePost(postViewModel: PostViewModel) {
        stopLoadingIndicator()
        feed_item_profile_image.loadAvatar(postViewModel.userEmail)
        feed_item_profile_name.text = postViewModel.postAuthor
        feed_item_profile_company.text = postViewModel.postAuthorCompany
        feed_item_title.text = postViewModel.postTitle
        feed_item_desc.text = postViewModel.postDesc
    }

    override fun onCommentsReceived(commentViewModels: List<CommentViewModel>) {
        populateComments(commentViewModels)
        cacheCommentModels(commentViewModels)
    }

    private fun populateComments(commentViewModels: List<CommentViewModel>) {
        stopLoadingIndicator()
        post_comments_header.setVisible()
        post_comments_header.text = resources.getQuantityString(R.plurals.comments_label, commentViewModels.size, commentViewModels.size)
        commentsAdapter.setItems(commentViewModels)
        scrollToSavedPosition()
    }

    override fun onError(reason: String) {
        stopLoadingIndicator()
        Snackbar.make(refreshIndicator, reason, Snackbar.LENGTH_LONG).show()
    }

    private fun initRecyclerView() {
        post_comments_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
    }

    private fun fetchData() {
        if (postViewCache.hasPostData()) populatePost(postViewCache.getPost()!!)
        else postPresenter.fetchPost()

        if (postViewCache.hasCommentsData()) populateComments(postViewCache.getComments()!!)
        else postPresenter.fetchComments()
    }

    private fun scrollToSavedPosition() {
        if (postScrollState != NO_SAVED_POSITION) {
            post_root_scrollview.scrollY = postScrollState
            postScrollState = NO_SAVED_POSITION
        }
    }

    private fun initialiseViews() {
        removeMaxLineLimits()
        initSwipeRefresh()
        initRecyclerView()
    }

    private fun initialiseCache() {
        postViewCache = ViewModelProviders.of(this).get(PostViewCache::class.java)
    }

    private fun cachePostModel(postViewModel: PostViewModel) {
        postViewCache.cachePost(postViewModel)
    }

    private fun cacheCommentModels(commentViewModels: List<CommentViewModel>) {
        postViewCache.cacheComments(commentViewModels)
    }

    private fun initSwipeRefresh() {
        refreshIndicator = post_swipe_refresh
        refreshIndicator.setOnRefreshListener { postPresenter.forceRefreshItems() }
        startLoadingIndicator()
    }

    private fun removeMaxLineLimits() {
        // We are re-using the Feed Item layout.
        // Remove restrictions that exists on Feed.
        feed_item_title.maxLines = Integer.MAX_VALUE
        feed_item_desc.maxLines = Integer.MAX_VALUE
    }
}
