package com.mpaani.goodfeed.feed

import com.mpaani.goodfeed.core.contract.BasePresenterContract
import com.mpaani.goodfeed.feed.viewmodel.FeedViewModel

interface FeedViewContract {

    /**
     * When we have successfully built the Feed Items.
     */
    fun onFeedItemsReceived(feedModels: List<FeedViewModel>)

    /**
     * When we want to navigate to the given Post.
     */
    fun onNavigateToPost(postId: Int, userName: String, userEmail: String)

    /**
     * When there was some error while processing feeds.
     * The reason is a user-readable input.
     */
    fun onError(reason: String)
}

interface FeedPresenterContract : BasePresenterContract {

    /**
     * Fetches the user feed.
     */
    fun fetchItems()

    /**
     * When the user taps on a given Feed.
     */
    fun postClicked(feedViewModel: FeedViewModel)

}
