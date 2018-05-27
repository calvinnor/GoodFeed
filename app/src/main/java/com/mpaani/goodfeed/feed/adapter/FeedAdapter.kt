package com.mpaani.goodfeed.feed.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.extension.loadAvatar
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.view_feed_item.view.*
import java.util.*

/**
 * Adapter class for showing User Feed.
 */
class FeedAdapter(private val feedListener: FeedListener) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val feedList: MutableList<FeedViewModel> = ArrayList()

    fun setItems(feedItems: List<FeedViewModel>) {
        feedList.apply {
            clear()
            addAll(feedItems)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_feed_item, parent, false))

    override fun getItemCount() = feedList.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) =
            holder.bind(feedList[position], feedListener)

    class FeedViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

        private val rootContainer = rootView.feed_item_root
        private val postAuthorImage = rootView.feed_item_profile_image
        private val postAuthorName = rootView.feed_item_profile_name
        private val postAuthorCompany = rootView.feed_item_profile_company
        private val postTitle = rootView.feed_item_title
        private val postDescription = rootView.feed_item_desc

        fun bind(feedItem: FeedViewModel, feedListener: FeedListener) {
            postAuthorImage.loadAvatar(feedItem.userEmail)
            postAuthorName.text = feedItem.postAuthor
            postAuthorCompany.text = feedItem.postAuthorCompany
            postTitle.text = feedItem.postTitle
            postDescription.text = feedItem.postDesc

            rootContainer.setOnClickListener {
                feedListener.onFeedItemClicked(feedItem)
            }
        }
    }

    /**
     * For clients that want to listen to Feed events like clicks.
     */
    interface FeedListener {

        /**
         * Called when the user taps on a given Feed item.
         */
        fun onFeedItemClicked(feedViewModel: FeedViewModel)
    }
}
