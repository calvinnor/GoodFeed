package com.mpaani.goodfeed.feed.ui

import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.ui.BaseActivity
import com.mpaani.goodfeed.core.ui.BaseFragment
import com.mpaani.goodfeed.feed.presenter.FeedPresenter

class FeedActivity : BaseActivity() {

    override val contentLayout = R.layout.activity_feed
    override val fragmentContainer = R.id.main_fragment_container
    override var fragment: BaseFragment? = null
        get() {
            // General case
            if (field != null) return field

            // On Activity destroy in background, Fragment is automatically created and attached
            var feedFragment = supportFragmentManager.findFragmentByTag(FeedFragment.TAG)

            feedFragment = if (feedFragment == null) FeedFragment() else feedFragment as FeedFragment

            field = feedFragment
            return field
        }

    override fun setupFragment(fragment: BaseFragment) {
        val feedFragment = fragment as FeedFragment
        feedFragment.setFeedPresenter(FeedPresenter(feedFragment))
    }

    override fun getToolbarTitle(): String = getString(R.string.feed_toolbar_title)
}
