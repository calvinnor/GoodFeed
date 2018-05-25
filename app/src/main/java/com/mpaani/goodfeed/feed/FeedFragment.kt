package com.mpaani.goodfeed.feed

import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.ui.BaseFragment

class FeedFragment : BaseFragment() {

    override val fragmentTag = TAG
    override val layout = R.layout.fragment_feed

    companion object {
        const val TAG = "FeedFragment"
    }
}
