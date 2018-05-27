package com.mpaani.goodfeed.post.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.mpaani.goodfeed.R
import com.mpaani.goodfeed.core.ui.BaseActivity
import com.mpaani.goodfeed.core.ui.BaseFragment
import com.mpaani.goodfeed.post.presenter.PostPresenter

/**
 * Activity for showing the Post.
 * This forms a container for the PostFragment.
 */
class PostActivity : BaseActivity() {

    companion object {

        private const val ARGS_POST_ID = "post_id"
        private const val ARGS_USER_EMAIL = "user_email"
        private const val ARGS_USER_NAME = "user_name"

        /**
         * Start the Post Activity with the given post IDs.
         */
        fun startActivity(context: Context, postId: Int, userName: String, userEmail: String) {
            val startIntent = Intent(context, PostActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putInt(ARGS_POST_ID, postId)
                    putString(ARGS_USER_EMAIL, userEmail)
                    putString(ARGS_USER_NAME, userName)
                })
            }
            context.startActivity(startIntent)
        }
    }

    override val contentLayout = R.layout.activity_post
    override val fragmentContainer = R.id.main_fragment_container
    override var fragment: BaseFragment? = null
        get() {
            // General case
            if (field != null) return field

            // On Activity destroy in background, Fragment is automatically created and attached
            var postFragment = supportFragmentManager.findFragmentByTag(PostFragment.TAG)

            postFragment = if (postFragment == null) PostFragment() else postFragment as PostFragment

            field = postFragment
            return field
        }

    override fun setupFragment(fragment: BaseFragment) {
        val postFragment = fragment as PostFragment

        val arguments = getIntentExtras()
        val postPresenter = PostPresenter(postFragment, arguments.getInt(ARGS_POST_ID), arguments.getString(ARGS_USER_EMAIL))
        postFragment.setPostPresenter(postPresenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBackOnToolbar()
    }

    private fun showBackOnToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getIntentExtras() = intent.extras
            ?: throw RuntimeException("Must pass bundle with postId, userName and userEmail")

    override fun getToolbarTitle(): String {
        val arguments = getIntentExtras()
        return getString(R.string.post_toolbar_title, arguments.getString(ARGS_USER_NAME))
    }
}
