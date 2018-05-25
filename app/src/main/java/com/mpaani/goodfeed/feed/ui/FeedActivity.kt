package com.mpaani.goodfeed.feed.ui

import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.ui.BaseActivity

class FeedActivity : BaseActivity() {

    override val contentLayout = R.layout.activity_feed
    override val fragmentContainer = R.id.main_fragment_container
    override val fragment: FeedFragment? = null
        get() {
            if (field != null) return field
            val fromFragmentManager = supportFragmentManager.findFragmentByTag(FeedFragment.TAG)
            return if (fromFragmentManager == null) FeedFragment() else fromFragmentManager as FeedFragment
        }
}
