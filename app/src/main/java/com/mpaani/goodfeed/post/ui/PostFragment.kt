package com.mpaani.goodfeed.post.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.extension.loadAvatar
import com.mpaani.goodfeed.core.extension.setVisible
import com.mpaani.goodfeed.core.ui.BaseFragment
import com.mpaani.goodfeed.post.PostPresenterContract
import com.mpaani.goodfeed.post.PostViewContract
import com.mpaani.goodfeed.post.adapter.CommentsAdapter
import com.mpaani.goodfeed.post.viewmodel.CommentViewModel
import com.mpaani.goodfeed.post.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.view_feed_item.*

/**
 * Container UI class for showing a feed Post.
 */
class PostFragment : BaseFragment(), PostViewContract {

    companion object {
        const val TAG = "PostFragment"

        private const val POST_SCROLL_STATE = "post_scroll_state"
    }

    override val fragmentTag = TAG
    override val layout = R.layout.fragment_post

    private lateinit var postPresenter: PostPresenterContract
    private val commentsAdapter = CommentsAdapter()

    private var postScrollState: Int = 0

    /**
     * Set this fragment's presenter.
     */
    fun setPostPresenter(postPresenterContract: PostPresenterContract) {
        this.postPresenter = postPresenterContract
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
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
        feed_item_profile_image.loadAvatar(postViewModel.userEmail)
        feed_item_profile_name.text = postViewModel.postAuthor
        feed_item_profile_company.text = postViewModel.postAuthorCompany
        feed_item_title.text = postViewModel.postTitle
        feed_item_desc.text = postViewModel.postDesc
    }

    override fun onCommentsReceived(commentViewModels: List<CommentViewModel>) {
        post_comments_header.setVisible()
        commentsAdapter.setItems(commentViewModels)
        scrollToSavedPosition()
    }

    override fun onError(reason: String) {
        Toast.makeText(context, reason, Toast.LENGTH_LONG).show()
    }

    private fun initRecyclerView() {
        post_comments_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }
    }

    private fun fetchData() {
        postPresenter.fetchPost()
        postPresenter.fetchComments()
    }

    private fun scrollToSavedPosition() {
        post_root_scrollview.scrollY = postScrollState
    }

    private fun initialiseViews() {
        // We are re-using the Feed Item layout.
        // Remove restrictions that exists on Feed.
        feed_item_title.maxLines = Integer.MAX_VALUE
        feed_item_desc.maxLines = Integer.MAX_VALUE
    }
}
