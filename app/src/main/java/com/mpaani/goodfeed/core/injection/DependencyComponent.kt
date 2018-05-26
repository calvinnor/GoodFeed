package com.mpaani.goodfeed.core.injection

import com.mpaani.goodfeed.core.ui.BaseActivity
import com.mpaani.goodfeed.core.ui.BaseFragment
import com.mpaani.goodfeed.feed.presenter.FeedPresenter
import com.mpaani.goodfeed.post.presenter.PostPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(DependencyProvider::class)])
interface DependencyComponent {

    fun inject(activity: BaseActivity)
    fun inject(fragment: BaseFragment)

    fun inject(feedPresenter: FeedPresenter)
    fun inject(postPresenter: PostPresenter)
}
