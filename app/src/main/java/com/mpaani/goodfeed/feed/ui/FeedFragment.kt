package com.mpaani.goodfeed.feed.ui

import android.os.Bundle
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.data.ApiProxy
import com.mpaani.goodfeed.core.injection.dependencyComponent
import com.mpaani.goodfeed.core.ui.BaseFragment
import javax.inject.Inject

class FeedFragment : BaseFragment() {

    companion object {
        const val TAG = "FeedFragment"
    }

    override val fragmentTag = TAG
    override val layout = R.layout.fragment_feed

    @Inject
    lateinit var apiProxy: ApiProxy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dependencyComponent.inject(this)
    }
}
