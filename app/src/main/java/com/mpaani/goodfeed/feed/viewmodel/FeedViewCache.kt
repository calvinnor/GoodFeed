package com.mpaani.goodfeed.feed.viewmodel

import android.arch.lifecycle.ViewModel

/**
 * In-memory cache for Feed.
 * Will be persisted across configuration changes.
 */
class FeedViewCache : ViewModel() {

    private var feedViewModels: List<FeedViewModel>? = null

    /**
     * Cache the Feed View Models.
     */
    fun cacheItems(feedItems: List<FeedViewModel>) {
        feedViewModels = feedItems
    }

    /**
     * Returns if we have cached any Feed ViewModel data.
     */
    fun hasData(): Boolean {
        feedViewModels.let {
            return it != null && it.isNotEmpty()
        }
    }

    /**
     * Get the Feed View Models.
     */
    fun getItems() = feedViewModels
}
